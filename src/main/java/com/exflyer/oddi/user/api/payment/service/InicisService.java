package com.exflyer.oddi.user.api.payment.service;

import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.api.payment.dao.PaymentMapper;
import com.exflyer.oddi.user.api.payment.dto.InicisAuthMobileResult;
import com.exflyer.oddi.user.api.payment.dto.InicisAuthResult;
import com.exflyer.oddi.user.api.payment.dto.InicisMobileReqResult;
import com.exflyer.oddi.user.api.payment.dto.InicisReqResult;
import com.exflyer.oddi.user.api.payment.dto.PaymentReq;
import com.exflyer.oddi.user.api.promotion.service.PromotionService;
import com.exflyer.oddi.user.config.InicisConfig;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Adv;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.models.PgAccreditLog;
import com.exflyer.oddi.user.models.PgPaymentLog;
import com.exflyer.oddi.user.repository.AdvRepository;
import com.exflyer.oddi.user.repository.CouponMappingRepository;
import com.exflyer.oddi.user.repository.CouponPromotionRepository;
import com.exflyer.oddi.user.repository.PaymentRepository;
import com.exflyer.oddi.user.repository.PgAccreditLogRepository;
import com.exflyer.oddi.user.repository.PgPaymentLogRepository;
import com.exflyer.oddi.user.repository.PromotionCouponRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.google.gson.Gson;
import com.inicis.std.util.HttpUtil;
import com.inicis.std.util.SignatureUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 이니시스 결제처리 서비스
 */
@Component
@Slf4j
public class InicisService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PgAccreditLogRepository pgAccreditLogRepository;

    @Autowired
    private PgPaymentLogRepository pgPaymentLogRepository;

    @Autowired
    private PromotionCouponRepository promotionCouponRepository;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponPromotionRepository couponPromotionRepository;

    @Autowired
    private CouponMappingRepository couponMappingRepository;

    @Autowired
    private InicisConfig inicisConfig;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private CouponPromotionService couponPromotionService;

    @Autowired
    private KakaoNotificationService kakaoNotificationService;

    @Autowired
    private InicisPaymentLogService inicisPaymentLogService;

    /**
     * 이니시스 인증요청용 데이타 생성
     *
     * @param paymentSeq 결제정보 시퀀스
     * @param couponMappingSeq 쿠폰코드
     * @return Map 인증정보
     */
    public Map reqAuthCondition(Long paymentSeq, String couponMappingSeq)
        throws Exception {

        Long timestamp = new Date().getTime();

        PaymentReq payment = paymentService.findPayment(paymentSeq, couponMappingSeq);

        Map condition = new HashMap();

        // 전문 버전 (1.0 고정)
        condition.put("version", "1.0");

        // 상점 아이디
        condition.put("mid", inicisConfig.getInicisMid());

        // 주문번호
        condition.put("oid", paymentSeq);

        // 결제금액
        condition.put("price", payment.getPrice());

        // 요청지불수단
        condition.put("gopaymethod", "Card");

        // 할부개월수
        condition.put("quotabase", "2:3:4:5:6:7:8:9:10:11:12");

        // SHA256 Hash값 (대상: mid와 매칭되는 signkey)
        condition.put("mKey", SignatureUtil.hash(inicisConfig.getInicisSignKey(), "SHA-256"));

        // SHA256 Hash값 (대상: oid, price, timestamp)
        condition.put("signature", SignatureUtil
            .hash(nvp(condition.get("oid"), condition.get("price"), timestamp), "SHA-256"));

        // 타임스탬프
        condition.put("timestamp", timestamp);

        // 통화구분
        condition.put("currency", "WON");

        // 상품명
        condition.put("goodname", payment.getGoodName());

        // 구매자명
        condition.put("buyername", payment.getBuyerName());

        // 구매자 휴대전화번호
        condition.put("buyertel", payment.getBuyerTel());

        // 구매자 이메일
        condition.put("buyeremail", payment.getBuyerEmail());

        // 최조결제금액
        condition.put("acceptmethod", "below1000");

        // 결과수신 URL
        condition.put("returnUrl",
            StringUtils.join(inicisConfig.getInicisSiteUrl(), "/user/payment/inicis/result/", paymentSeq, "/", couponMappingSeq));

        // 경제창 닫기  URL
        condition.put("closeUrl",
            StringUtils.join(inicisConfig.getInicisSiteUrl(), "/user/payment/inicis/close/", paymentSeq, "/", couponMappingSeq));

        return condition;
    }

    /**
     * 이니시스 결제처리
     *
     * @param inicisReqResult 인증요청 결과
     * @param paymentSeq 결제정보 시퀀스
     * @param couponMappingSeq 쿠폰 시퀀스
     * @return PaymentAuthResult 결제처리 결과
     */
    public InicisAuthResult paymentProcessing(InicisReqResult inicisReqResult, Long paymentSeq, Long couponMappingSeq) throws Exception {

        InicisAuthResult inicisAuthResult = new InicisAuthResult();
        String timestamp = SignatureUtil.getTimestamp();

        Map signatureParam = new HashMap();
        signatureParam.put("authToken", inicisReqResult.getAuthToken());
        signatureParam.put("timestamp", timestamp);

        Map authParam = new HashMap();
        authParam.put("mid", inicisReqResult.getMid());
        authParam.put("authToken", inicisReqResult.getAuthToken());
        authParam.put("signature", SignatureUtil.makeSignature(signatureParam));
        authParam.put("timestamp", timestamp);
        authParam.put("charset", inicisReqResult.getCharset());
        authParam.put("format", "JSON");

        try {

            //결제전 쿠폰사용확인
            //신청상태일때만 쿠폰사용확인
            Payment payment = paymentRepository.findPaymentInfo(paymentSeq);
            String advTitle = payment.getAdvName();
            if(StringUtil.isBlank(payment.getAdvName())) {
                advTitle = payment.getProductName();
            }


            inicisPaymentLogService.paymentLog("=========[/payment/inicis/result/{paymentSeq}/{couponMappingSeq}]================");
            inicisPaymentLogService.paymentLog("paymentSeq", String.valueOf(paymentSeq));
            inicisPaymentLogService.paymentLog("paymentType", payment.getType());
            inicisPaymentLogService.paymentLog("couponMappingSeq",
                String.valueOf(couponMappingSeq));
            log.info("=================================================================================");

            //결제완료, 신청취소가 아닐경우
            if (!"PGT002".equals(payment.getType()) && !"PGT005".equals(payment.getType())) {
                if (couponMappingSeq == 0 || couponPromotionService.isValidMemberCouponCode(payment.getMemberId(), couponMappingSeq)) {

                    inicisReqResult.setPaymentSeq(paymentSeq);
                    inicisReqResult.setSignature((String) authParam.get("signature"));
                    inicisReqResult.setTimestamp(timestamp);

                    PgAccreditLog pgAccreditLog = new PgAccreditLog();
                    pgAccreditLog.setPgAccreditLog(inicisReqResult);

                    /**
                     * Todo PG 결제인증 정보 이력 저장
                     */
                    pgAccreditLogRepository.save(pgAccreditLog);

                    if ("0000".equals(inicisReqResult.getResultCode())) {
                        HttpUtil httpUtil = new HttpUtil();

                        try {
                            inicisAuthResult = new Gson().fromJson(httpUtil.processHTTP(authParam, inicisReqResult.getAuthUrl()),InicisAuthResult.class);

                            //승인결과 성공시
                            if ("0000".equals(inicisAuthResult.getResultCode()) && inicisAuthResult.getTotPrice() != null && StringUtils.isNotBlank(inicisAuthResult.getPayMethod())) {

                                try{

                                    /**
                                     * Todo 서비스 - 결제 성공 로직 작성
                                     * Todo PG - PG 이력 처리
                                     * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                                     */
                                    savePaymentLog(payment, inicisAuthResult,"PGT002");

                                    /**
                                     * Todo 프로모션 쿠폰 사용처리하기
                                     */
                                    couponMappingRepository.saveByMemberCouponUsing(true,LocalDateUtils.krNow(), payment.getMemberId(), couponMappingSeq,paymentSeq);

                                    /**
                                     * Todo 광고 상태값 변경
                                     */
                                    Optional<Adv>  advOptional = advRepository.findById(payment.getAdvSeq());
                                    Adv adv = advOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

                                    adv.setPaymentSeq(payment.getSeq());
                                    adv.setProgressCode("PGT002");
                                    advRepository.save(adv);

                                }catch(Exception e) {

                                    //결제DB 저장 실패.
                                    inicisAuthResult.setResultCode("9999");
                                    inicisAuthResult.setResultMsg("결제DB 저장 실패.");

                                    /**
                                     * Todo 서비스 - 결제 실패 로직 작성
                                     *              1. 로직 에러
                                     *              2. db 에러
                                     */
                                    log.info("============================[ paymentProcessing ERROR START ]===========================");
                                    log.info("inicisAuthResult {}",  "[paymentSeq : "+paymentSeq+"]", "inicis db insert error");
                                    log.info("============================[ //paymentProcessing ERROR END ]===========================");

                                    log.error("============================[ paymentProcessing 결제DB 저장 실패 ERROR START ]===========================");
                                    log.error("inicisAuthResult {}",  "[paymentSeq : "+paymentSeq+"]", "inicis db insert error");
                                    log.error("couponMappingSeq {}", couponMappingSeq);

                                    log.error("pg_accredit_log start==========================================");
                                    for(Field field : pgAccreditLog.getClass().getDeclaredFields()) {
                                        field.setAccessible(true);
                                        Object value = field.get(pgAccreditLog);
                                        log.error("key: {}, value : {}", field.getName(), value);
                                    }
                                    log.error("//pg_accredit_log end==========================================");

                                    log.error("inicisAuthResult start==========================================");
                                    for(Field field : inicisAuthResult.getClass().getDeclaredFields()) {
                                        field.setAccessible(true);
                                        Object value = field.get(inicisAuthResult);
                                        log.error("key: {}, value : {}", field.getName(), value);
                                    }
                                    log.error("//inicisAuthResult end============================================");

                                    log.error("============================[ //paymentProcessing 결제DB 저장 실패 ERROR END ]===========================");

                                    /**
                                     * Todo 서비스 - 결제 성공 로직 작성
                                     * Todo PG - PG 이력 처리
                                     * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                                     */
                                    savePaymentLog(payment, inicisAuthResult,"PGT003");

                                }
                            } else {

                                /**
                                 * Todo 서비스 - 결제 성공 로직 작성
                                 * Todo PG - PG 이력 처리
                                 * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                                 */
                                savePaymentLog(payment, inicisAuthResult,"PGT003");

                                inicisAuthResult.setResultCode("9999");
                                inicisAuthResult.setResultMsg("결제승인 실패 되었습니다.\n" + inicisAuthResult.getResultMsg());

                                inicisPaymentLogService.paymentLog("=================================================================================");
                                inicisPaymentLogService.paymentLog("WEB 결제승인 실패 되었습니다.\n" + inicisAuthResult.getResultMsg());
                                inicisPaymentLogService.paymentLog("=================================================================================");


                                //승인결과 실패시 망취소
                                inicisAuthResult = new Gson().fromJson(httpUtil.processHTTP(authParam, inicisReqResult.getNetCancelUrl()), InicisAuthResult.class);

                            }

                        } catch (Exception e) {

                            /**
                             * Todo 서비스 - 결제 실패 로직 작성
                             * 1. 통신 에러
                             */
                            //망취소하기
                            inicisAuthResult = new Gson().fromJson(httpUtil.processHTTP(authParam, inicisReqResult.getNetCancelUrl()),InicisAuthResult.class);

                            //결제통신오류
                            inicisAuthResult.setResultCode("9999");
                            inicisAuthResult.setResultMsg("결제통신오류로 실패 하였습니다.");

                            /**
                             * Todo 서비스 - 결제 성공 로직 작성
                             * Todo PG - PG 이력 처리
                             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                             */
                            savePaymentLog(payment, inicisAuthResult,"PGT003");

                            log.error("=================================================================================");
                            log.error("paymentSeq{}", "["+paymentSeq+"]", "["+ advTitle+"] 결제통신 에러가 발생하였습니다.");
                            log.error("=================================================================================");
                        }

                    } else {

                        //결제인증 실패
                        inicisAuthResult.setResultCode("9999");
                        inicisAuthResult.setResultMsg("결제인증에 실패 하였습니다.");

                        /**
                         * Todo 서비스 - 결제 성공 로직 작성
                         * Todo PG - PG 이력 처리
                         * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                         */
                        savePaymentLog(payment, inicisAuthResult,"PGT003");

                        inicisPaymentLogService.paymentLog("=================================================================================");
                        inicisPaymentLogService.paymentLog("paymentProcessing :결제인증에 실패 하였습니다.");
                        inicisPaymentLogService.paymentLog("=================================================================================");
                    }
                } else {
                    //프로모션 쿠폰
                    inicisAuthResult.setResultCode("9999");
                    inicisAuthResult.setResultMsg("프로모션 쿠폰 적용 실패 하였습니다.");

                    /**
                     * Todo 서비스 - 결제 성공 로직 작성
                     * Todo PG - PG 이력 처리
                     * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                     */
                    savePaymentLog(payment, inicisAuthResult,"PGT003");


                    inicisPaymentLogService.paymentLog("=================================================================================");
                    inicisPaymentLogService.paymentLog("프로모션 쿠폰 적용 실패 하였습니다. couponMappingSeq : "+ couponMappingSeq);
                    inicisPaymentLogService.paymentLog("=================================================================================");
                }
            } else {
                String progressCode = payment.getType();

                if ("PGT002".equals(progressCode)) {
                    inicisAuthResult.setResultMsg("이미 결제완료된 건입니다.");
                } else if ("PGT004".equals(progressCode)) {
                    inicisAuthResult.setResultMsg("결제취소된 건입니다.");
                } else if ("PGT005".equals(progressCode)) {
                    inicisAuthResult.setResultMsg("신청취소된 건입니다.");
                }

                // 결제완료, 결제취소, 신청취소
                inicisAuthResult.setResultCode("9999");

                /**
                 * Todo 서비스 - 결제 성공 로직 작성
                 * Todo PG - PG 이력 처리
                 * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003, 결제취소 PGT004, 신청취소 PGT005
                 */
                savePaymentLog(payment, inicisAuthResult, progressCode);

                inicisPaymentLogService.paymentLog("=================================================================================");
                inicisPaymentLogService.paymentLog(inicisAuthResult.getResultMsg() + " paymentSeq : " + payment.getSeq());
                inicisPaymentLogService.paymentLog("=================================================================================");
            }
        } catch (Exception e) {

            HttpUtil httpUtil = new HttpUtil();
            String cancelResult = httpUtil.processHTTP(authParam, inicisReqResult.getNetCancelUrl());

            inicisAuthResult.setResultCode("9999");
            inicisAuthResult.setResultMsg("결제실패 하였습니다.");

            /**
             * Todo PG - PG 이력 처리
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
             */
            PgPaymentLog pgPaymentLog = new PgPaymentLog(inicisAuthResult);
            pgPaymentLog.setPaymentSeq(paymentSeq);
            pgPaymentLogRepository.save(pgPaymentLog);
            log.info("authResult {}", inicisAuthResult);

            inicisPaymentLogService.paymentLog("====[결제실패 하였습니다 cancelResult]============================================");
            inicisPaymentLogService.paymentLog(cancelResult);
            inicisPaymentLogService.paymentLog("=================================================================================");
        }
        return inicisAuthResult;
    }

    /**
     * 이니시스 결제중지 처리
     *
     * @param paymentSeq 결제정보 시퀀스
     * @param couponMappingSeq 쿠폰시퀀스
     */
    public void paymentClose(Long paymentSeq, Long couponMappingSeq, String memberId) {
        /**
         * Todo 결제시도하지 않았을 때의 로직 작성
         * 결제데이터 삭제
         * 쿠폰 취소처리
         * 광고 쿠폰사용 null 처리
         */
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentSeq);
        Payment payment = paymentOptional.orElseGet(Payment::new);
        advRepository.updateCouponCode(payment.getAdvSeq(), memberId);
        couponMappingRepository.updateCouponCancel(memberId, couponMappingSeq);
    }

    /**
     * name and value parameter (알파벳 순서로 조합하여야 한다)
     *
     * @param oid 주문번호
     * @param price 결제금액
     * @param timestamp 시간
     * @return String
     */
    private String nvp(Object oid, Object price, Object timestamp) {
        return StringUtils.join("oid=", oid, "&price=", price, "&timestamp=", timestamp);
    }

    /**
     * 모바일 이니시스 인증요청용 데이타 생성
     *
     * @param paymentSeq 결제정보 시퀀스
     * @param couponMappingSeq 쿠폰코드
     * @return Map 인증정보
     */
    public Map reqAuthMobileCondition(Long paymentSeq, String couponMappingSeq) throws Exception {

        Long timestamp = new Date().getTime();

        PaymentReq payment = paymentService.findPayment(paymentSeq, couponMappingSeq);

        Map condition = new HashMap();

        // 전문 버전 (1.0 고정)
        condition.put("version", "1.0");

        // 상점 아이디
        condition.put("mid", inicisConfig.getInicisMid());

        // 주문번호
        condition.put("oid", paymentSeq);

        // 결제금액
        condition.put("price", payment.getPrice());

        // 요청지불수단
        condition.put("gopaymethod", "Card");

        // 상품명
        condition.put("goodname", payment.getGoodName());

        // 구매자명
        condition.put("buyername", payment.getBuyerName());

        // 결과수신 URL
        condition.put("returnUrl",
            StringUtils.join(inicisConfig.getInicisSiteUrl(), "/user/payment/inicis/mobile/result/", paymentSeq, "/", couponMappingSeq));

        return condition;
    }

    /**
     * 모바일 이니시스 결제처리
     *
     * @param inicisMobileReqResult 인증요청 결과
     * @param paymentSeq 결제정보 시퀀스
     * @param couponMappingSeq 쿠폰 시퀀스
     * @return PaymentAuthResult 결제처리 결과
     */
    public InicisAuthMobileResult paymentMobileProcessing(InicisMobileReqResult inicisMobileReqResult, Long paymentSeq, Long couponMappingSeq) throws Exception {

        InicisAuthMobileResult inicisAuthMobileResult = new InicisAuthMobileResult();
        String timestamp = SignatureUtil.getTimestamp();

        Map authParam = new HashMap();

        authParam.put("P_MID", inicisConfig.getInicisMid());
        authParam.put("P_TID", inicisMobileReqResult.getP_TID());

        //결제전 쿠폰사용확인
        //신청상태일때만 쿠폰사용확인
        Payment payment = paymentRepository.findPaymentInfo(paymentSeq);
        String advTitle = payment.getAdvName();
        if(StringUtil.isBlank(payment.getAdvName())) {
            advTitle = payment.getProductName();
        }

        inicisPaymentLogService.paymentLog("paymentType", payment.getType());
        //결제완료, 신청취소가 아닐경우
        if (!"PGT002".equals(payment.getType()) && !"PGT005".equals(payment.getType())) {
            if (couponMappingSeq == 0 || couponPromotionService.isValidMemberCouponCode(payment.getMemberId(), couponMappingSeq)) {

                inicisMobileReqResult.setPaymentSeq(paymentSeq);

                PgAccreditLog pgAccreditLog = new PgAccreditLog();
                inicisMobileReqResult.setMid(inicisConfig.getInicisMid());
                pgAccreditLog.setMobilePgAccreditLog(inicisMobileReqResult);

                /**
                 * Todo PG 결제인증 정보 이력 저장
                 */
                pgAccreditLogRepository.save(pgAccreditLog);

                if ("00".equals(inicisMobileReqResult.getP_STATUS())) {
                    HttpClient client = new HttpClient();

                    String P_REQ_URL = inicisMobileReqResult.getP_REQ_URL() + "?P_TID=" + inicisMobileReqResult.getP_TID() + "&P_MID=" + inicisConfig.getInicisMid();
                    GetMethod method = new GetMethod(P_REQ_URL);
                    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                        new DefaultHttpMethodRetryHandler(3, false));

                    try{
                        int statusCode = client.executeMethod(method);
                        inicisPaymentLogService.paymentLog("statusCode ####::::::::: " + statusCode );

                        if (statusCode != HttpStatus.SC_OK) {
                            inicisPaymentLogService.paymentLog("Method failed: " + method.getStatusLine());
                        }else {

                            InputStream rstream = method.getResponseBodyAsStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(rstream, "euc-kr"));

                            String line;
                            String resultString = "";
                            while ((line = br.readLine()) != null) {
                                resultString += line;
                            }
                            br.close();

                            String[] values = resultString.split("&");
                            HashMap<String, String> map = new HashMap<String, String>();

                            for( int x = 0; x < values.length; x++ ){
                                String[] value = values[x].split("=");
                                String key = value[0];
                                if(value.length > 1) {
                                    String val =  val = value[1];
                                    map.put(key.trim(), val.trim());
                                }else {
                                    map.put(key.trim(), "");
                                }
                            }

                            inicisPaymentLogService.paymentLog(map);
                            inicisAuthMobileResult.setInicisAuthMobileResult(map);
                            String resultCode = map.get("P_STATUS");
                            inicisPaymentLogService.paymentLog("resultCode ==> "+ resultCode);
                            if("00".equals(resultCode)) {

                                /**
                                 * Todo 서비스 - 결제 성공 로직 작성
                                 * oodo PG - PG 이력 처리
                                 * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                                 */
                                savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT002");

                                /**
                                 * Todo 프로모션 쿠폰 사용처리하기
                                 */
                                couponMappingRepository.saveByMemberCouponUsing(true,LocalDateUtils.krNow(), payment.getMemberId(), couponMappingSeq,paymentSeq);
                                /**
                                 * Todo 광고 상태값 변경
                                 */
                                Optional<Adv>  advOptional = advRepository.findById(payment.getAdvSeq());
                                Adv adv = advOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

                                adv.setPaymentSeq(payment.getSeq());
                                adv.setProgressCode("PGT002");
                                advRepository.save(adv);

                            }else {

                                /**
                                 * Todo 서비스 - 결제 성공 로직 작성
                                 * Todo PG - PG 이력 처리
                                 * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                                 */
                                savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT003");

                                inicisAuthMobileResult.setResultCode("9999");
                                inicisAuthMobileResult.setResultMsg(map.get("P_RMESG1"));
                            }
                        }
                    } catch (HttpException e) {

                        //결제통신오류
                        inicisAuthMobileResult.setResultCode("9999");
                        inicisAuthMobileResult.setResultMsg("결제통신오류로 실패 하였습니다.");

                        log.error("============================[ paymentMobileProcessing ERROR START ]===========================");
                        log.error("paymentSeq{}", "["+paymentSeq+"]", "["+ advTitle+"] 결제통신 에러가 발생하였습니다.");
                        log.error("============================[ paymentMobileProcessing ERROR END ]===========================");

                        /**
                         * Todo 서비스 - 결제 성공 로직 작성
                         * Todo PG - PG 이력 처리
                         * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                         */
                        savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT003");

                        e.printStackTrace();

                    } catch (IOException e) {

                        //결제DB 저장 실패.
                        inicisAuthMobileResult.setResultCode("9999");
                        inicisAuthMobileResult.setResultMsg("결제DB 저장 실패.");

                        log.info("============================[ paymentMobileProcessing ERROR START ]===========================");
                        log.info("inicisAuthMobileResult {}",  "[paymentSeq : "+paymentSeq+"]", "inicis db insert error");
                        log.info("============================[ //paymentMobileProcessing ERROR END ]===========================");

                        log.error("============================[ paymentMobileProcessing 결제DB 저장 실패 ERROR START ]===========================");
                        log.error("inicisAuthResult {}",  "[paymentSeq : "+paymentSeq+"]", "inicis db insert error");
                        log.error("couponMappingSeq {}", couponMappingSeq);

                        log.error("pg_accredit_log start==========================================");
                        for(Field field : pgAccreditLog.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            Object value = field.get(pgAccreditLog);
                            log.error("key: {}, value : {}", field.getName(), value);
                        }
                        log.error("//pg_accredit_log end==========================================");

                        log.error("inicisAuthMobileResult start==========================================");
                        for(Field field : inicisAuthMobileResult.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            Object value = field.get(inicisAuthMobileResult);
                            log.error("key: {}, value : {}", field.getName(), value);
                        }
                        log.error("//inicisAuthMobileResult end============================================");

                        log.error("============================[ paymentMobileProcessing 결제DB 저장 실패 ERROR END ]===========================");

                        /**
                         * Todo 서비스 - 결제 성공 로직 작성
                         * Todo PG - PG 이력 처리
                         * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                         */
                        savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT003");

                        e.printStackTrace();
                    } finally {
                        method.releaseConnection();
                    }
                } else {

                    //결제인증 실패
                    inicisAuthMobileResult.setResultCode("9999");
                    inicisAuthMobileResult.setResultMsg("결제인증에 실패 하였습니다.");

                    inicisPaymentLogService.paymentLog("=================================================================================");
                    inicisPaymentLogService.paymentLog("MOBILE 결제인증에 실패 하였습니다.");
                    inicisPaymentLogService.paymentLog("=================================================================================");

                    /**
                     * Todo 서비스 - 결제 성공 로직 작성
                     * Todo PG - PG 이력 처리
                     * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                     */
                    savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT003");

                }
            } else {
                //프로모션 쿠폰
                inicisAuthMobileResult.setResultCode("9999");
                inicisAuthMobileResult.setResultMsg("프로모션 쿠폰 적용 실패 하였습니다.");

                inicisPaymentLogService.paymentLog("=================================================================================");
                inicisPaymentLogService.paymentLog("MOBILE 프로모션 쿠폰 적용 실패 하였습니다. couponMappingSeq "+ couponMappingSeq);
                inicisPaymentLogService.paymentLog("=================================================================================");

                /**
                 * Todo 서비스 - 결제 성공 로직 작성
                 * Todo PG - PG 이력 처리
                 * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
                 */
                savePaymentMobileLog(payment, inicisAuthMobileResult,"PGT003");
            }
        } else {
            String progressCode = payment.getType();

            if ("PGT002".equals(progressCode)) {
                inicisAuthMobileResult.setResultMsg("이미 결제완료된 건입니다.");
            } else if ("PGT004".equals(progressCode)) {
                inicisAuthMobileResult.setResultMsg("결제취소된 건입니다.");
            } else if ("PGT005".equals(progressCode)) {
                inicisAuthMobileResult.setResultMsg("신청취소된 건입니다.");
            }

            // 결제완료, 결제취소, 신청취소
            inicisAuthMobileResult.setResultCode("9999");

            /**
             * Todo 서비스 - 결제 성공 로직 작성
             * Todo PG - PG 이력 처리
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003, 결제취소 PGT004, 신청취소 PGT005
             */
            savePaymentMobileLog(payment, inicisAuthMobileResult, progressCode);

            inicisPaymentLogService.paymentLog("=================================================================================");
            inicisPaymentLogService.paymentLog("MOBILE " + inicisAuthMobileResult.getResultMsg() + " paymentSeq : " + payment.getSeq());
            inicisPaymentLogService.paymentLog("=================================================================================");
        }

        return inicisAuthMobileResult;
    }

    public void savePaymentMobileLog(Payment payment,InicisAuthMobileResult inicisAuthMobileResult, String progressCode) {

        try{

            /**
             * Todo 서비스 - 결제 성공 로직 작성
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
             */
            payment.setPayment(inicisAuthMobileResult, progressCode);
            paymentRepository.save(payment);

            /**
             * Todo PG - PG 이력 처리
             */
            PgPaymentLog pgPaymentLog = new PgPaymentLog();
            pgPaymentLog.setPgPaymentLog(inicisAuthMobileResult);
            pgPaymentLog.setPaymentSeq(payment.getSeq());
            pgPaymentLogRepository.save(pgPaymentLog);

        }catch(Exception e) {

            /**
             * Todo 서비스 - 결제 성공 로직 작성
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
             */
            payment.setPayment(inicisAuthMobileResult, progressCode);
            paymentRepository.save(payment);

            /**
             * Todo PG - PG 이력 처리
             */
            PgPaymentLog pgPaymentLog = new PgPaymentLog();
            pgPaymentLog.setPgPaymentLog(inicisAuthMobileResult);
            pgPaymentLog.setPaymentSeq(payment.getSeq());
            pgPaymentLogRepository.save(pgPaymentLog);

            log.error("--- [ savePaymentMobileLog => db save fail ]=====================");
            //결제DB 저장 실패.
            inicisAuthMobileResult.setResultCode("9999");
            inicisAuthMobileResult.setResultMsg("모바일 결제DB 저장 실패.");
        }

    }

    public void savePaymentLog(Payment payment,InicisAuthResult inicisAuthResult, String progressCode)
        throws IllegalAccessException {

        try{

            /**
             * Todo 서비스 - 결제 성공 로직 작성
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
             */
            payment.setPayment(inicisAuthResult, progressCode);
            paymentRepository.save(payment);

            /**
             * Todo PG - PG 이력 처리
             */
            PgPaymentLog pgPaymentLog = new PgPaymentLog(inicisAuthResult);
            pgPaymentLog.setPaymentSeq(payment.getSeq());
            pgPaymentLogRepository.save(pgPaymentLog);
            inicisPaymentLogService.paymentLog("PG PAYMENT SAVE LOG ====");
            inicisPaymentLogService.paymentLog(inicisAuthResult);

        }catch(Exception e) {

            /**
             * Todo 서비스 - 결제 성공 로직 작성
             * 신청 PGT001, 결제완료 PGT002, 결제실패 PGT003
             */
            payment.setPayment(inicisAuthResult, progressCode);
            paymentRepository.save(payment);

            /**
             * Todo PG - PG 이력 처리
             */
            PgPaymentLog pgPaymentLog = new PgPaymentLog(inicisAuthResult);
            pgPaymentLog.setPaymentSeq(payment.getSeq());
            pgPaymentLogRepository.save(pgPaymentLog);

            log.error("--- [ savePaymentLog Exception => db save fail ]=====================");
            log.error("progressCode {}", progressCode);
            //결제DB 저장 실패.
            inicisAuthResult.setResultCode("9999");
            inicisAuthResult.setResultMsg("WEB 결제DB 저장 실패.");

            for(Field field : inicisAuthResult.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(inicisAuthResult);
                log.error("key: {}, value : {}", field.getName(), value);
            }
        }
    }
}



package com.exflyer.oddi.user.api.payment.service;

import com.exflyer.oddi.user.annotaions.OddiEncrypt;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvFileRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerDetailRes;
import com.exflyer.oddi.user.api.adv.adv.service.AdvService;
import com.exflyer.oddi.user.api.adv.oddi.dao.OddiMapper;
import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.api.payment.dao.PaymentMapper;
import com.exflyer.oddi.user.api.payment.dto.InicisCancelReq;
import com.exflyer.oddi.user.api.payment.dto.InicisCancelRes;
import com.exflyer.oddi.user.api.payment.dto.PaymentCancelReq;
import com.exflyer.oddi.user.api.payment.dto.PaymentCancelRes;
import com.exflyer.oddi.user.api.payment.dto.PaymentCouponReq;
import com.exflyer.oddi.user.api.payment.dto.PaymentCouponRes;
import com.exflyer.oddi.user.api.payment.dto.PaymentDetailResult;
import com.exflyer.oddi.user.api.payment.dto.PaymentReq;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.config.InicisConfig;
import com.exflyer.oddi.user.enums.AdvProcessCodes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Adv;
import com.exflyer.oddi.user.models.Coupon;
import com.exflyer.oddi.user.models.CouponMapping;
import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.models.PartnerConfig;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.models.PgPaymentLog;
import com.exflyer.oddi.user.models.Product;
import com.exflyer.oddi.user.repository.AdvPartnerRepository;
import com.exflyer.oddi.user.repository.AdvRepository;
import com.exflyer.oddi.user.repository.CouponMappingRepository;
import com.exflyer.oddi.user.repository.CouponRepository;
import com.exflyer.oddi.user.repository.PatnerConfigRepository;
import com.exflyer.oddi.user.repository.PaymentRepository;
import com.exflyer.oddi.user.repository.PgPaymentLogRepository;
import com.exflyer.oddi.user.repository.ProductRepository;
import com.exflyer.oddi.user.repository.PromotionCouponRepository;
import com.exflyer.oddi.user.repository.jpa.MemberTermsRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.inicis.std.util.HttpUtil;
import com.inicis.std.util.SignatureUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class PaymentService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OddiMapper oddiMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AdvRepository advRepository;

    @Autowired
    private PgPaymentLogRepository pgPaymentLogRepository;

    @Autowired
    private PatnerConfigRepository patnerConfigRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMappingRepository couponMappingRepository;

    @Autowired
    private CouponPromotionService couponPromotionService;

    @Autowired
    private InicisConfig inicisConfig;

    @Autowired
    private InicisPaymentLogService inicisPaymentLogService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 결제정보 조회
     *
     * @param paymentSeq
     * @return
     */
    @OddiEncrypt
    public PaymentReq findPayment(Long paymentSeq, String couponCode) {
        return paymentMapper.findByPayment(paymentSeq);
    }

    public PaymentDetailResult findList(Long advSeq, String id) throws ApiException {

        PaymentDetailResult resultList = paymentMapper.findAdvDetail(advSeq);
        List<AdvFileRes> findAdvFileList = oddiMapper.findAdvFileList(advSeq); //파일조회
        List<TermsServiceRes> termsList = oddiMapper.findTermsList(id);         //약관조회

        List<PartnerConfig> partnerConfig =  patnerConfigRepository.findAll();  //파트너설정(광고취소일자)

        int totalPrice = 0;

        List<AdvPartnerDetailRes> advPartnerResList = null;

        Integer diffMonth = LocalDateUtils.diffMonth(resultList.getStartDate(),resultList.getEndDate());

        //묶음상품이 아닐경우는 신청금액 합산
        if(resultList.getProductSeq() == null) {
            advPartnerResList = oddiMapper.findAdvPartnerList(advSeq); //파트너조회
            for (AdvPartnerDetailRes advPartnerDetailRes: advPartnerResList) {
                totalPrice += advPartnerDetailRes.getSlotPrice() * advPartnerDetailRes.getRequestSlot()*diffMonth;
            }
        } else {

            advPartnerResList = oddiMapper.findAdvPartnerProductList(resultList.getProductSeq(), resultList.getAdvSeq());
//            for (AdvPartnerDetailRes advPartnerDetailRes: advPartnerResList) {
//                totalPrice += advPartnerDetailRes.getSlotPrice() * advPartnerDetailRes.getRequestSlot();
//            }

            Optional<Product> productOptional = productRepository.findById(resultList.getProductSeq());
            Product product = productOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));
            totalPrice += product.getPrice()*diffMonth;
        }

        resultList.setPartnerConfig(partnerConfig);
        resultList.setTotalPrice(totalPrice);
        resultList.setAdvFileList(findAdvFileList);
        resultList.setAdvPartnerList(advPartnerResList);
        resultList.setMemberTerms(termsList);
        return resultList;
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public Long save(PaymentReq req) throws ApiException {

        Adv adv = advRepository.getOne(req.getAdvSeq());
        if(AdvProcessCodes.PROGRESS_RECEIPT_CANCEL.getCode().equals(adv.getProgressCode())){
            throw new ApiException(ApiResponseCodes.ADV_TIME_CACNE);
        }

        Integer dicountPrice = 0 ;

        if(req.getCouponMappingSeq() != 0) {
            PaymentCouponReq paymentCouponReq = new PaymentCouponReq(req.getMemberId(), req.getPromotionChannelType());
            paymentCouponReq.setCouponMappingSeq(req.getCouponMappingSeq());
            List<PaymentCouponRes> couponList = couponPromotionService.couponList(paymentCouponReq);
            if(!CollectionUtils.isEmpty(couponList)) {
                dicountPrice = couponList.get(0).getDiscountPrice();
            }
        }

        int originAdvPrice = adv.getPrice();
        int advPrice = adv.getPrice() - dicountPrice;
        if(advPrice != 0) {advPrice += advPrice*10/100;}

        if(advPrice < 1) {
            advPrice = 0;
            req.setType("PGT002");
            req.setSuccess(true);
            req.setResponseCode("0000");
            req.setRegDate(LocalDateUtils.krNow());
        }

        req.setPrice(advPrice);
        Payment payment = new Payment(req);
        paymentRepository.save(payment);
        if(req.getSeq() == null) {
            em.persist(payment);
        }

        if (0 < originAdvPrice) {
            long couponMappingSeq = StringUtils.isNotBlank(adv.getCouponNumber()) ? Long.parseLong(adv.getCouponNumber()) : 0L;

            if (couponMappingSeq != req.getCouponMappingSeq()) {
                couponMappingRepository.saveByMemberCouponUsing(false,null,req.getMemberId(), couponMappingSeq, null);

                if (0 < req.getCouponMappingSeq() && (advPrice == 0)) { // 10
                    couponMappingRepository.saveByMemberCouponUsing(true,req.getRegDate(),req.getMemberId(), req.getCouponMappingSeq(), payment.getSeq());
                }
            }
        }

        adv.setPaymentSeq(payment.getSeq());
        adv.setCouponNumber(String.valueOf(req.getCouponMappingSeq()));
        adv.setDiscountPrice(BigDecimal.valueOf(dicountPrice));
        adv.setProgressCode(req.getType());
        adv.setModDate(LocalDateUtils.krNow());

        advRepository.save(adv);
        return payment.getSeq();
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public InicisCancelRes paymentCancel(Long paymentSeq,  Long advSeq,Boolean isMobile, String memberId) throws ApiException, Exception {

        String inicisMid = inicisConfig.getInicisMid();
        InicisCancelRes inicisCancelRes = new InicisCancelRes();//결과값 리턴
        PaymentCancelReq paymentCancelReq = new PaymentCancelReq(paymentSeq, advSeq);
        PaymentCancelRes paymentCancelRes = paymentMapper.fnidPaymentInfo(paymentCancelReq);

        if(paymentCancelRes == null) {
            throw new ApiException(ApiResponseCodes.NOT_FOUND);
        }

        Optional<Adv> advOptional = advRepository.findById(advSeq);
        Adv adv = advOptional.orElseGet(Adv::new);

        //결제안하고 광고취소했을경우
        if("PGT001".equals(paymentCancelRes.getType())) {

            if(StringUtils.isNotBlank(adv.getCouponNumber()) && !"0".equals(adv.getCouponNumber())) {
                couponCancel(memberId, Long.valueOf(adv.getCouponNumber()));
            }
            advRepository.updateCancelProgress(AdvProcessCodes.PROGRESS_RECEIPT_CANCEL.getCode(),advSeq, LocalDateUtils.krNow(), memberId);
            inicisCancelRes.setResultCode("00");
            inicisCancelRes.setResultMsg("광고취소");
            return inicisCancelRes;
        }

        //금액이 0일경우 광고취소, 광고상태가 신청상태일 경우
        if(paymentCancelRes.getPrice() < 1 && ("PGT001".equals(paymentCancelRes.getType()) || "PGT002".equals(paymentCancelRes.getType()))) {

            inicisCancelRes.setResultCode("00");
            inicisCancelRes.setResultMsg("정상완료");
            //payment에 광고취소
            paymentRepository.saveByIdType(AdvProcessCodes.PROGRESS_PAYMENT_CANCEL.getCode(),paymentSeq,memberId,LocalDateUtils.krNow(),isMobile);
            advRepository.updateCancelProgress(AdvProcessCodes.PROGRESS_PAYMENT_CANCEL.getCode(),advSeq, LocalDateUtils.krNow(), memberId);
            //프로모션쿠폰 취소
            if(StringUtils.isNotBlank(adv.getCouponNumber()) && !"0".equals(adv.getCouponNumber())) {
                couponCancel(memberId, Long.valueOf(adv.getCouponNumber()));
            }

            return inicisCancelRes;
        }

        // hash(INIAPIKey+ type + paymethod + timestamp + clientIp + mid + tid)
        String hashData = inicisConfig.getInicisKey() + "Refund"+ paymentCancelRes.getPayMethod() + LocalDateUtils.krNowByFormatter("yyyyMMddHHmmss")
            + inicisConfig.getInicisClientIpl() + inicisMid + paymentCancelRes.getTid();
        paymentCancelRes.setHashData(SignatureUtil.hash(hashData, "SHA-512"));
        paymentCancelRes.setMid(inicisMid);

        Map map = new HashMap();
        map.put("InicisKey", inicisConfig.getInicisKey());
        map.put("PayMethod", paymentCancelRes.getPayMethod());
        map.put("InicisClientIpl", inicisConfig.getInicisClientIpl());
        map.put("Mid", inicisMid);
        map.put("Tid", paymentCancelRes.getTid());
        map.put("hashData", hashData);
        inicisPaymentLogService.paymentLog(map);

        InicisCancelReq inicisCancelReq = new InicisCancelReq(paymentCancelRes, inicisConfig.getInicisClientIpl());

        //이니시스 취소URL 호출
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> paramMap = objectMapper.convertValue(inicisCancelReq, Map.class);
        HttpUtil httpUtil = new HttpUtil();
        inicisCancelRes = new Gson().fromJson(httpUtil.processHTTP(paramMap, inicisConfig.getInicisCancelUrl()), InicisCancelRes.class);
        inicisPaymentLogService.paymentLog("ResultCode",inicisCancelRes.getResultCode());

        try{
            if("00".equals(inicisCancelRes.getResultCode()) || "01".equals(inicisCancelRes.getResultCode())) {

                PgPaymentLog pgPaymentLog = new PgPaymentLog();
                pgPaymentLog.setCancelPgPayment(inicisCancelRes,paymentCancelRes, isMobile);
                pgPaymentLogRepository.save(pgPaymentLog);

                /**
                 * TODO 결제취소, 광고취소, 쿠폰취소
                 */
                paymentRepository.saveByIdType(AdvProcessCodes.PROGRESS_PAYMENT_CANCEL.getCode(),paymentSeq, memberId,LocalDateUtils.krNow(),isMobile);
                advRepository.updateCancelProgress(AdvProcessCodes.PROGRESS_PAYMENT_CANCEL.getCode(),advSeq, LocalDateUtils.krNow(), memberId);

                inicisPaymentLogService.paymentLog("CouponNumber",adv.getCouponNumber());
                //프로모션쿠폰 취소
                if(StringUtils.isNotBlank(adv.getCouponNumber()) && !"0".equals(adv.getCouponNumber())) {
                    couponCancel(memberId, Long.valueOf(adv.getCouponNumber()));
                }
            }else {

                PgPaymentLog pgPaymentLog = new PgPaymentLog();
                pgPaymentLog.setCancelPgPayment(inicisCancelRes,paymentCancelRes, isMobile);
                pgPaymentLogRepository.save(pgPaymentLog);

                log.error("============================[ paymentCancel]===========================");
                log.error("getResultCode {}",  "["+inicisCancelRes.getResultCode()+"]");
                log.error("getResultMsg {}",  "["+inicisCancelRes.getResultMsg()+"]");
                log.error("hashData {}", hashData);
                log.error("============================[ //paymentCancel]===========================");

                inicisPaymentLogService.paymentLog("=== [paymentCancel return error start] ===");
                inicisPaymentLogService.paymentLog(paymentCancelRes);
                inicisPaymentLogService.paymentLog("=== [paymentCancel return error end] ===");
            }

        }catch(Exception e) {


            log.error("============================[ paymentCancel ERROR START ]===========================");
            log.error("inicisCancelRes e {}", e);
            log.error("inicisCancelRes {}",  "["+paymentCancelRes.getPaymentSeq()+"]", "["+paymentCancelRes.getAdvTitle()+"]","["+ paymentCancelRes.getBuyerName() +"] 결제취소후 DB저장시 에러가 발생하였습니다.");
            log.error("============================[ paymentCancel ERROR END ]===========================");

            log.info("============================[ paymentCancel ERROR START ]===========================");
            log.info("inicisCancelRes {}",  "[advSeq : "+paymentCancelRes.getPaymentSeq()+"]", "inicis db insert error");
            log.info("============================[ paymentCancel ERROR END ]===========================");

            inicisPaymentLogService.paymentLog("============================[ paymentCancel Exception START ]===========================");
            inicisPaymentLogService.paymentLog(paymentCancelRes);
            inicisPaymentLogService.paymentLog("============================[ paymentCancel Exception END ]===========================");

//            PgPaymentLog pgPaymentLog = new PgPaymentLog();
//            pgPaymentLog.setCancelPgPayment(inicisCancelRes,paymentCancelRes, isMobile);
//            pgPaymentLogRepository.save(pgPaymentLog);

        }

        return inicisCancelRes;
    }

    public void couponCancel(String memberId, Long couponMappingSeq) {
        couponMappingRepository.updateCouponCancel(memberId,couponMappingSeq);

        Optional<CouponMapping> couponMappingOptional = couponMappingRepository.findById(couponMappingSeq);
        CouponMapping couponMapping = couponMappingOptional.orElseGet(CouponMapping::new);
        if(couponMapping != null) {
            Coupon coupon = couponRepository.getOne(couponMapping.getCouponSeq());
            coupon.setCouponCount(coupon.getCouponCount() == 0 ? 0 : (coupon.getCouponCount()-1));
            couponRepository.save(coupon);
        }
    }

    public Payment findPaymentList(Long paymentSeq, String id) {
        return paymentRepository.findPaymentList(paymentSeq, id);
    }
}

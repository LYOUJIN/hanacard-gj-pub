package com.exflyer.oddi.user.api.mustad.service;


import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.files.config.AwsS3PublicConfig;
import com.exflyer.oddi.user.api.files.service.AwsS3Service;
import com.exflyer.oddi.user.api.mustad.dao.MustadMapper;
import com.exflyer.oddi.user.api.mustad.dto.AwsKakaoAuthReq;
import com.exflyer.oddi.user.api.mustad.dto.AwsKakaoAuthRes;
import com.exflyer.oddi.user.api.mustad.dto.FederatedAuth;
import com.exflyer.oddi.user.api.mustad.dto.FederatedAuthRes;
import com.exflyer.oddi.user.api.mustad.dto.KakaoAccessTokenInfo;
import com.exflyer.oddi.user.api.mustad.dto.KakaoMustadAuthorize;
import com.exflyer.oddi.user.api.mustad.dto.KakaoToken;
import com.exflyer.oddi.user.api.mustad.dto.MemeberAdvStateResult;
import com.exflyer.oddi.user.api.mustad.dto.MustadKakaoRes;
import com.exflyer.oddi.user.api.mustad.dto.MustadMemberRes;
import com.exflyer.oddi.user.api.mustad.dto.MustadNotificationReq;
import com.exflyer.oddi.user.api.mustad.dto.MustadResult;
import com.exflyer.oddi.user.api.mustad.dto.MustadVerificationNumberReq;
import com.exflyer.oddi.user.api.mustad.dto.ProviderMetadata;
import com.exflyer.oddi.user.api.user.account.dto.MemberAddReq;
import com.exflyer.oddi.user.api.user.account.dto.TermsReq;
import com.exflyer.oddi.user.api.user.account.service.MemberAccountService;
import com.exflyer.oddi.user.api.user.auth.dao.LoginConfigMapper;
import com.exflyer.oddi.user.api.user.auth.dto.CertificationResult;
import com.exflyer.oddi.user.api.user.auth.dto.SignInReq;
import com.exflyer.oddi.user.api.user.auth.service.PassWordEncrypt;
import com.exflyer.oddi.user.api.voc.terms.dao.TermsMapper;
import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.config.KakaoAuthorizeConfig;
import com.exflyer.oddi.user.config.MustadConfig;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.NotificationCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.jwt.dto.JwtResult;
import com.exflyer.oddi.user.jwt.service.JwtService;
import com.exflyer.oddi.user.message.service.MessageService;
import com.exflyer.oddi.user.models.CouponPromotion;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.MemberTerms;
import com.exflyer.oddi.user.models.NotificationGroup;
import com.exflyer.oddi.user.models.NotificationTarget;
import com.exflyer.oddi.user.models.NotificationTargetGroup;
import com.exflyer.oddi.user.repository.CouponPromotionRepository;
import com.exflyer.oddi.user.repository.FilesRepository;
import com.exflyer.oddi.user.repository.NotificationGroupRepository;
import com.exflyer.oddi.user.repository.NotificationTargetGroupRepository;
import com.exflyer.oddi.user.repository.NotificationTargetRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.repository.jpa.MemberTermsRepository;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class MustadService {

    @Autowired
    KakaoAuthorizeConfig kakaoAuthorizeConfig;

    @Autowired
    MustadConfig mustadConfig;

    @Autowired
    private AwsS3PublicConfig awsS3PublicConfig;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private Gson gson;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginConfigMapper loginConfigMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PassWordEncrypt passWordEncrypt;

    @Autowired
    private MemberTermsRepository memberTermsRepository;

    @Autowired
    private CouponPromotionService couponPromotionService;

    @Autowired
    private CouponPromotionRepository couponPromotionRepository;

    @Autowired
    private AesEncryptor aesEncryptor;

    @Autowired
    private MustadMapper mustadMapper;

    @Autowired
    private TermsMapper termsMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @Autowired
    private NotificationTargetRepository notificationTargetRepository;

    @Autowired
    private NotificationTargetGroupRepository notificationTargetGroupRepository;

    @PersistenceContext
    EntityManager em;

    public static final String FEDERATEDAUTH_PARAM = "cognito-idp.us-west-2.amazonaws.com";

    public static final String SUCCESS_RESULT_CODE = "0000";
    public static final String ERROR_RESULT_CODE = "9999";
    public static final String X_APP_VERSION = "2.7.14";

    public static final String ACCESS_TOKEN_INFO = "/v1/user/access_token_info";

    public static final String KAKAO_LOGOUT = "/v1/user/logout";

    public static final String IS_MOBILE = "MOBILE";

    /**
     * 카카오 토큰 요청
     * @param code
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public MustadKakaoRes goMustadKakaoToken(String code) throws ApiException, Exception {

        MustadKakaoRes res = new MustadKakaoRes();
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoReq = new KakaoToken(code, kakaoAuthorizeConfig);

        try {

            String fullUrl = kakaoAuthorizeConfig.getOauthHost() + kakaoAuthorizeConfig.getTokenUri();

            HttpPost httpPost = new HttpPost(fullUrl);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity(new Gson().toJson(kakaoReq), "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                res.setResultCode(String.valueOf(response.getStatusLine().getStatusCode()));
                res.setResultMessage("HttpStatus Error");
                return res;
            }

            String resBody = EntityUtils.toString(response.getEntity());

            MustadKakaoRes result = gson.fromJson(resBody, MustadKakaoRes.class);

            if (StringUtils.isBlank(result.getError())) {
                res.setResultCode(SUCCESS_RESULT_CODE);
                res.setResultMessage("정상처리");
            } else {
                res.setError(result.getError());
                res.setResultCode(result.getError_code());
                res.setResultMessage(result.getError_description());
            }

        } catch (Exception e) {
            res.setResultCode(ERROR_RESULT_CODE);
            res.setResultMessage("호출 실패.");
        }

        return res;
    }

    public KakaoAccessTokenInfo goMustadKakaoAccessTokenInfo(String accessToken) throws ApiException, Exception {
        KakaoAccessTokenInfo result = new KakaoAccessTokenInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        String fullUrl = kakaoAuthorizeConfig.getApiHost() + ACCESS_TOKEN_INFO;

        try {

            HttpGet httpGet = new HttpGet(fullUrl);
            httpGet.setHeader("Authorization", "Bearer " + accessToken);
            URI uri = new URIBuilder(httpGet.getURI()).build();
            httpGet.setURI(uri);
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                result.setResultCode(String.valueOf(response.getStatusLine().getStatusCode()));
                result.setResultMessage("HttpStatus Error");
                return result;
            }

            String resBody = EntityUtils.toString(response.getEntity());
            result = gson.fromJson(resBody, KakaoAccessTokenInfo.class);

            if(result != null) {
                result.setResultCode(SUCCESS_RESULT_CODE);
                result.setResultMessage("정상처리");
            }else {
                result.setResultCode(ERROR_RESULT_CODE);
                result.setResultMessage("사용자 토큰이 유효하지 않습니다.\n 다시 로그인 하기시 바랍니다.");
                //로그아웃처리하기
            }

        } catch (Exception e) {
            result.setResultCode(ERROR_RESULT_CODE);
            result.setResultMessage("호출 실패.");
        }

        return result;
    }

    public KakaoAccessTokenInfo goMustadKakaoLogout(String accessToken) throws ApiException, Exception {
        KakaoAccessTokenInfo result = new KakaoAccessTokenInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        String fullUrl = kakaoAuthorizeConfig.getApiHost() + KAKAO_LOGOUT;

        try {

            HttpPost httpPost = new HttpPost(fullUrl);
            httpPost.setHeader("Authorization", "Bearer " + accessToken);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpPost);

            String resBody = EntityUtils.toString(response.getEntity());

            result = gson.fromJson(resBody, KakaoAccessTokenInfo.class);

            if(result != null) {
                result.setResultCode(SUCCESS_RESULT_CODE);
                result.setResultMessage("로그아웃 되었습니다.");
            }else {
                result.setResultCode(ERROR_RESULT_CODE);
                result.setResultMessage("로그아웃 실패.");
                log.debug("== [ goMustadKakaoLogout ] ================================");
                log.debug("로그아웃 실패");
                log.debug("== [ //goMustadKakaoLogout ] ==============================");
            }

        } catch (Exception e) {
            result.setResultCode(ERROR_RESULT_CODE);
            result.setResultMessage("호출 실패.");
            log.debug("== [ goMustadKakaoLogout Exception] ================================");
            log.debug("호출 실패, {}", e);
            log.debug("== [ //goMustadKakaoLogout Exception] ==============================");
        }

        return result;
    }

    /**
     * 머스타드 인증요청
     * 1단계 카카오 인증토큰을 서비스 인증 (Cognito 토큰으로 반환받음)
     * @param awsKakaoAuthReq
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public AwsKakaoAuthRes goMustadAuthorize(AwsKakaoAuthReq awsKakaoAuthReq) {

        AwsKakaoAuthRes res = new AwsKakaoAuthRes();
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoMustadAuthorize kakaoMustadReq = new KakaoMustadAuthorize(awsKakaoAuthReq);

        try {

            String fullUrl = kakaoAuthorizeConfig.getKakaoAwsAuth();

            HttpPost httpPost = new HttpPost(fullUrl);
            httpPost.setHeader("Authorization", "user " + awsKakaoAuthReq.getUserAccessToken());
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "raw");
            httpPost.setEntity(new StringEntity(new Gson().toJson(kakaoMustadReq), "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);


            String resBody = EntityUtils.toString(response.getEntity());

            Map resultMap = gson.fromJson(resBody, Map.class);

            /**
             * TODO 카카오 인증 error 리턴 값이 있을경우 error 없으면 토큰 값 리턴해줌.
             */
            if (resultMap.get("error") != null) {
                res.setResultCode(resultMap.get("code").toString());
                res.setResultMessage(resultMap.get("message").toString());
            } else {
                res = gson.fromJson(resultMap.get("AuthenticationResult").toString(),AwsKakaoAuthRes.class);
                res.setResultCode(SUCCESS_RESULT_CODE);
                res.setResultMessage("카카오 인증 성공");
            }
        } catch (Exception e) {
            res.setResultCode(ERROR_RESULT_CODE);
            res.setResultMessage("호출 실패.");
        }

        return res;
    }


    /**
     * 머스타드 federatedAuth 호출
     * 2단계 Cognito 토큰으로 서비스 사용자 토큰으로 변환
     *
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public FederatedAuthRes goFederatedAuth(String type, String idToken) {

        log.info("머스타드 federatedAuth 호출 START =============================================");

        FederatedAuthRes federatedAuthRes = new FederatedAuthRes();
        String fullUrl = mustadConfig.getFederatedAuth();
        HttpPost httpPost = null;
        HttpResponse response = null;

        try{
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put(getProviderKey(type), idToken);

            FederatedAuth federatedAuth = new FederatedAuth(paramMap, mustadConfig.getClientId());

            httpPost = new HttpPost(fullUrl);
            RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setConnectionRequestTimeout(5 * 1000)
                .setSocketTimeout(5 * 1000).build();
            httpPost.setConfig(config);
            httpPost.setHeader("x-device-platform", "ADR");
            httpPost.setHeader("x-device-country", "US");
            httpPost.setHeader("x-device-language", "en-US");
            httpPost.setHeader("x-device-type", "M01");
            httpPost.setHeader("x-app-version", "2.7.15");
            httpPost.setHeader("isEnableAdPush", "1");
            httpPost.setHeader("isEnableAdEmail","1" );
            httpPost.setHeader("x-geo-lon", "120");
            httpPost.setHeader("x-geo-lat", "33");
            httpPost.setHeader("x-is-enable-ad-push", "1");
            httpPost.setHeader("zenabled-ad-push", "1");
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(federatedAuth), "UTF-8"));
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                federatedAuthRes.setResultCode(ERROR_RESULT_CODE);
                federatedAuthRes.setResultMessage("Token Error " + String.valueOf(response.getStatusLine().getStatusCode()));
            } else {
                String resBody = EntityUtils.toString(response.getEntity());

                Map resultMap = gson.fromJson(resBody, Map.class);

                if(resultMap != null) {
                    if (resultMap.get("error") != null) {
                        Map errorMap = gson.fromJson(resultMap.get("error").toString(),Map.class);
                        federatedAuthRes.setResultCode(errorMap.get("code").toString());
                        federatedAuthRes.setResultMessage(errorMap.get("message").toString());
                    }else {
                        Map resMap = (Map) resultMap.get("result");
                        resMap.put("resultCode", SUCCESS_RESULT_CODE);
                        resMap.put("resultMessage", "사용자 토큰 변환 성공");
                        federatedAuthRes.setFederatedAuthRes(resMap);
                    }
                }else {
                    federatedAuthRes.setResultCode(ERROR_RESULT_CODE);
                    federatedAuthRes.setResultMessage("사용자 토큰이 존재하지 않습니다.");
                }
            }

            log.info("// 머스타드 federatedAuth 호출 END MSG:{},{}", federatedAuthRes.getResultCode(), federatedAuthRes.getResultMessage());

        } catch(Exception e) {
            federatedAuthRes.setResultCode(ERROR_RESULT_CODE);
            federatedAuthRes.setResultMessage("사용자 토큰변환 실패.");
            log.info("// 머스타드 federatedAuth 호출 사용자 토큰변환 실패 END ========================");
        } finally {
            if (null != response) {
                EntityUtils.consumeQuietly(response.getEntity());
            }

            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }

        return federatedAuthRes;
    }

    /**
     * 머스타드 컨텐츠 조회
     * @param idToken
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public MustadResult goMustadContentList(String idToken) throws ApiException, Exception {

        MustadResult result = new MustadResult();
        HttpGet httpGet = null;
        HttpResponse response = null;

        log.info("머스타드 컨텐츠 조회 goMustadContentList 호출 START =============================================");

        String fullUrl = mustadConfig.getMyContent();

        try{
            httpGet = new HttpGet(fullUrl);
            httpGet.setHeader("Authorization", "user " + idToken);
            URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("page", "1")
                .addParameter("provider", "SignageContent")
                .addParameter("resourcetype", "Content.userboardlist")
                .addParameter("partnerCode", "ODDI")
                .build();
            httpGet.setURI(uri);
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                result.setResultCode(String.valueOf(response.getStatusLine().getStatusCode()));
                result.setResultMessage("HttpStatus Error");
                return result;
            }

            String resBody = EntityUtils.toString(response.getEntity());
            Map resultMap = gson.fromJson(resBody, Map.class);

            if(resultMap != null) {
                if (resultMap.get("error") != null) {
                    Map errorMap = gson.fromJson(resultMap.get("error").toString(),Map.class);
                    result.setResultCode(errorMap.get("code").toString());
                    result.setResultMessage(errorMap.get("message").toString());
                }else {
                    Map resMap = (Map) resultMap.get("result");
                    if(resMap != null) {
                        List<ProviderMetadata> resourcesList = (List<ProviderMetadata>)resMap.get("resources");
                        log.debug("====" + resourcesList );
                        result.setProviderMetadata(resourcesList);
                        result.setResultCode(SUCCESS_RESULT_CODE);
                        result.setResultMessage("정상처리");
                        result.setMustadToken("user " + idToken);
                    }
                }

            }else {
                result.setResultCode(ERROR_RESULT_CODE);
                result.setResultMessage("사용자 토큰이 존재하지 않습니다.");
            }

            log.info("// 머스타드 컨텐츠 조회  END ===== MSG :{},{}", result.getResultCode(), result.getResultMessage());
        } catch(Exception e) {
            result.setResultCode(ERROR_RESULT_CODE);
            result.setResultMessage("머스타드 컨텐츠 조회 오류.");

            log.info("// 머스타드 컨텐츠 조회 오류. END =============================================");
        } finally {
            if (null != response) {
                EntityUtils.consumeQuietly(response.getEntity());
            }

            if (null != httpGet) {
                httpGet.releaseConnection();
            }
        }

        return result;
    }

    /**
     * 머스타드 회원가입
     * 1. 머스타드 토큰으로 머스타드 회원정보 조회
     * 2. 이메일 + provider 존재여부 확인
     * 3. 없다면 오디 회원가입 있다면 로그인
     * @param token,request
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public CertificationResult signin(String token,HttpServletRequest request) throws ApiException {

        //로그인구분(모바일,웹)
        Map loginTypeMap = getLoginMobileType(request);
        SignInReq signInReq = new SignInReq();
        signInReq.setLoginType((Boolean) loginTypeMap.get("loginType"));
        signInReq.setLoginMobileType(loginTypeMap.get("loginMobileType").toString());

        MemberAddReq memberAddReq = new MemberAddReq();

        //사용자정보 조회
        memberAddReq = getUserProfileHttp(memberAddReq,token);

        if (!memberAddReq.getUserprofile()) {
            if (memberAddReq.getErrorMap() != null) {
                Map errorMap = gson.fromJson(memberAddReq.getErrorMap().get("error").toString(), Map.class);
                log.debug("USERPROFILE ERROR CODE: {}, message:{}",
                    errorMap.get("code").toString(), errorMap.get("message").toString());
            }
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_USERPROFILE_DATA);
        }

        //스토어프로파일 조회
        memberAddReq = getStoreProfilesHttp(memberAddReq,token);

        if (!memberAddReq.getStoreprofiles()) {

            if (memberAddReq.getErrorMap() != null) {
                Map errorMap = gson.fromJson(memberAddReq.getErrorMap().get("error").toString(), Map.class);
                log.debug("STOREPROFILES ERROR CODE: {}, message:{}",
                    errorMap.get("code").toString(), errorMap.get("message").toString());
            }
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_STOREPROFILES_DATA);
        }

        if("".equals(memberAddReq.getProvider())) {
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_PROVIDER_NOT_FOUND);
        }
        if("".equals(memberAddReq.getEmail())) {
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_EMAIL_NOT_FOUND);
        }
        if("".equals(memberAddReq.getPhoneNumber())) {
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_TEL_NOT_FOUND);
        }

        int isMemberId = memberRepository.isDuplicationId(memberAddReq.getEmail(),memberAddReq.getProvider());

        //회원가입
        if(isMemberId < 1) {
            memberAddReq.setMustadToken(token);
            addMember(memberAddReq);
        }
        signInReq.setEmail(memberAddReq.getEmail());
        return jwtResult(signInReq, memberAddReq.getProvider());
    }

    /**
     * 머스타드 회원 자동로그인
     * @param signInReq
     * @return
     * @throws ApiException
     */
    public CertificationResult jwtResult(SignInReq signInReq, String provider) throws ApiException {

        Member member = loginConfigMapper.findAllByMember(signInReq.getEmail(), provider);

        if (member == null) {
            throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
        }

        JwtResult jwtResult = jwtService.createAccessToken(member.getId(), 0, member.getPasswordReset(),provider);

        member.setLoginType(signInReq.isLoginType());
        member.setLoginMobileType(signInReq.getLoginMobileType());
        member.setPasswordErrorCount(0);
        member.setLoginDate(LocalDateUtils.krNow());
        memberRepository.save(member);

        return new CertificationResult(jwtResult);
    }

    /**
     * 회원가입 후 머스타드 api 호출
     * @param mustadId
     * @param userId
     * @throws ApiException
     */
    public void signinSend(String mustadToken,String mustadId,String oddiId) throws ApiException {

        try{

            Map paramMap = new HashMap();
            paramMap.put("userIdByMustad",mustadId);
            paramMap.put("userIdByOddi", oddiId);

            HttpPost httpPost = new HttpPost(mustadConfig.getMustadSigninUrl());
            httpPost.setHeader("Authorization", "user " + mustadToken);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(paramMap), "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                log.debug("", ApiResponseCodes.ERROR_MUSTAD_API.getMessage());
                return;
            }
            String resBody = EntityUtils.toString(response.getEntity());

            Map resMap = gson.fromJson(resBody, Map.class);
            if (resMap == null) {
                log.debug("", ApiResponseCodes.ERROR_MUSTAD_DATA.getMessage());
                return;
            }

            memberRepository.updateMustadMappingDone(mustadId,LocalDateUtils.krNow());

        } catch(Exception e) {
            return;
        }

    }

    /**
     * 머스타드 신규회원가입
     * @param memberAddReq
     * @throws ApiException
     */
    public void addMember(MemberAddReq memberAddReq) throws ApiException {
        memberAddReq.setId(memberAccountService.idConvertToUuid());
        memberAddReq.setPassword(passWordEncrypt.encryptPassword(memberAddReq.getPassword()));

        Member member = new Member(memberAddReq);
        memberRepository.save(member);

        String id = member.getId();
        //회원가입 후 머스타드 api 호출(사용자 등록)
        signinSend(memberAddReq.getMustadToken(),memberAddReq.getMustadId(),member.getId());

        //머스타드 회원약관 가입
        List<TermsServiceRes> termsServiceRes = termsMapper.findTermsService(3L);

        if(!CollectionUtils.isEmpty(termsServiceRes)) {

            List<MemberTerms> memberTerms = new ArrayList<>();
            termsServiceRes.forEach(data->{
                memberTerms.add(new MemberTerms(member.getId(), data.getSeq(), true));
            });
            memberTermsRepository.saveAll(memberTerms);
        }

        //가입쿠폰발급(가입,정액)
        CouponPromotion promotionList = couponPromotionRepository.findAllBySeq("PTC001", "PDT001");
        if(promotionList != null) {
            couponPromotionService.saveCoupon(member.getId(), promotionList);
        }
    }

    /**
     * 머스타드 회원정보변경
     * @param request
     * @return
     * @throws ApiException
     * @throws Exception
     */
    public Map modifyMustadMember(String token) throws Exception {

        Map resultMap = new HashMap();
        Boolean flag = false;
        String oddiId = "";

        if(StringUtils.isBlank(token)) {
            log.debug("modifyMustadMember token null {}", ApiResponseCodes.ERROR_MUSTAD_HEADER_API);
        }

        MemberAddReq memberAddReq = new MemberAddReq();

        //사용자정보 조회
        memberAddReq = getUserProfileHttp(memberAddReq,token);

        //스토어프로파일 조회
        memberAddReq = getStoreProfilesHttp(memberAddReq,token);

        if (memberAddReq.getUserprofile() && memberAddReq.getStoreprofiles()) {
            Member memberInfo = memberRepository.findMustadId(memberAddReq.getMustadId());

            if(memberInfo == null) {
                log.debug("modifyMustadMember mustadId not found {}", ApiResponseCodes.ERROR_MUSTAD_NOT_FOUND);
            }else {

                flag = true;
                //이메일 수신여부,문자수신여부 꼭 체크하기
                //회원정보수정
                memberInfo.setName(memberAddReq.getName());
                memberInfo.setEmail(memberAddReq.getEmail());
                memberInfo.setReceiveConsent(memberAddReq.isReceiveConsent());
                memberInfo.setEmailReceiveConsent(memberAddReq.isEmailReceiveConsent());
                memberInfo.setPhoneNumber(memberAddReq.getPhoneNumber());
                memberInfo.setProvider(memberAddReq.getProvider());
                oddiId = memberInfo.getId();

                memberRepository.save(memberInfo);
            }
        }

        resultMap.put("flag", flag);
        resultMap.put("mustadId", memberAddReq.getMustadId());
        resultMap.put("oddiId", oddiId);

        return resultMap;
    }

    /**
     * 스토어프로파일 조회
     * @param req
     * @param idToken
     * @return
     * @throws ApiException
     */
    public MemberAddReq getStoreProfilesHttp(MemberAddReq req, String idToken) {

        Boolean flag = false;

        try{

            HttpGet httpGet = new HttpGet(mustadConfig.getStoreprofiles());
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "user " + idToken);
            URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("userid", req.getMustadId())
                .build();
            httpGet.setURI(uri);
            HttpResponse response = getHttpResponse(httpGet);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                log.debug("getStoreProfilesHttp StatusCode {}", ApiResponseCodes.ERROR_MUSTAD_STOREPROFILES_API);
            }

            String resBody = EntityUtils.toString(response.getEntity());
            Map resultMap = gson.fromJson(resBody, Map.class);

            if (resultMap != null) {
                if (resultMap.get("error") != null) {
                    req.setErrorMap(gson.fromJson(resultMap.get("error").toString(), Map.class));
                }else {
                    List<Map> contents = (ArrayList<Map>)resultMap.get("contents");
                    contents.forEach(data -> {
                        Map locationDetails = (Map)data.get("locationDetails");

                        if(data.containsKey("attributes")){
                            Map attributes = (Map)data.get("attributes");
                            req.setPhoneNumber(aesEncryptor.encrypt(attributes.get("auth_info_telno").toString()));
                        }else {
                            log.debug("getStoreProfilesHttp tel not found {}", ApiResponseCodes.ERROR_MUSTAD_TEL_NOT_FOUND);
                        }
                    });
                    flag = true;
                }
            }

        } catch(Exception e) {
            log.debug("getStoreProfilesHttp Exception {}", ApiResponseCodes.ERROR_MUSTAD_API);
        }

        req.setStoreprofiles(flag);
        return req;
    }

    /**
     * 사용자 정보 조회
     * @param req
     * @param idToken
     * @return
     * @throws ApiException
     */
    public MemberAddReq getUserProfileHttp(MemberAddReq req, String idToken) {

        Boolean flag = false;

        try{

            HttpGet httpGet = new HttpGet(mustadConfig.getUserprofile());
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "user " + idToken);
            httpGet.setHeader("x-device-country", "KR");
            HttpResponse response = getHttpResponse(httpGet);

            if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                log.debug("getUserProfileHttp StatusCode {}", ApiResponseCodes.ERROR_MUSTAD_USERPROFILE_API);
            }

            String resBody = EntityUtils.toString(response.getEntity());
            Map resultMap = gson.fromJson(resBody, Map.class);

            if (resultMap != null) {
                if (resultMap.get("error") != null) {
                    req.setErrorMap(gson.fromJson(resultMap.get("error").toString(), Map.class));
                }else {
                    Map result = (Map)resultMap.get("result");
                    req.setMustadId(result.get("id").toString());
                    req.setName(result.get("lastName").toString() + result.get("firstName").toString());
                    req.setEmail(aesEncryptor.encrypt(result.get("email").toString()));
                    req.setProvider(result.get("provider").toString());

                    if (result.containsKey("isAdEmail")) {
                        req.setEmailReceiveConsent((Boolean)result.get("isAdEmail"));
                    } else {
                        req.setEmailReceiveConsent(false);
                    }

                    if (result.containsKey("isAdSms")) {
                        req.setReceiveConsent((Boolean)result.get("isAdSms"));
                    } else {
                        req.setReceiveConsent(true);
                    }

                    flag = true;
                }
            }

        } catch(Exception e) {
            log.debug("getUserProfileHttp Exception {}", ApiResponseCodes.ERROR_MUSTAD_API);
        }

        req.setUserprofile(flag);
        return req;
    }

    /**
     * 머스타드 대기/광고 상태 조회 API
     * @param mustadId
     * @throws ApiException
     */
    public MemeberAdvStateResult findMustadAdvState(String mustadId) throws Exception {
        String today = LocalDateUtils.krNow().format(DateTimeFormatter.ofPattern(("yyyyMMdd")));
        return mustadMapper.findMustadInfo(mustadId, today);
    }

    /**
     * 머스타드 회원탈퇴
     * @param mustadId
     * @throws Exception
     */
    public Map modifyMustadResignUser(String mustadId) throws Exception {

        Boolean flag = false;
        Member member = memberRepository.findMustadId(mustadId);
        String oddiId = "";

        if(member != null) {
            memberAccountService.modifyMyResign(member.getId());
            flag = true;
            oddiId = member.getId();
        }

        Map map = new HashMap();
        map.put("flag", flag);
        map.put("mustadId", mustadId);
        map.put("oddiId", oddiId);

        return map ;
    }

    public void sendVerificationNumber(MustadVerificationNumberReq req) throws Exception {
        String message = String.format("[머스타드] 인증 번호는 [%s] 입니다.", req.getVerificationNumber());
        log.debug("인증번호 : {} ", req.getVerificationNumber());
        messageService.send(message, req.getPhoneNumber(), "mustad");
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void sendMessage(MustadNotificationReq req) throws ApiException {

        if(CollectionUtils.isEmpty(req.getSendPhoneNumberList())) {
            throw new ApiException(ApiResponseCodes.ERROR_MUSTAD_SMS_NOT_TEL);
        }

        req.setName(NotificationCodes.MUSTAD_MANAGER_NAME.getName());
        req.setRegId(NotificationCodes.MUSTAD_MANAGER_ID.getName());
        req.setTargetCode("NTC008");//전화번호직접입력
        req.setReservationDate("0");

        NotificationTargetGroup notificationTargetGroup = new NotificationTargetGroup(req);

        notificationTargetGroupRepository.save(notificationTargetGroup);
        em.persist(notificationTargetGroup);

        req.setTargetGroupSeq(notificationTargetGroup.getSeq());
        saveNotificationGroup(req);
        req.getSendPhoneNumberList().forEach(data -> {

            if(StringUtils.isNotBlank(data)) {
                req.setSendPhoneNumber(data);
                saveNotificationTarget(req, data, null);
            }
        });
    }

    //그룹등록
    private void saveNotificationGroup(MustadNotificationReq sendReq){
        sendReq.setTargetCreateDone(false);
        sendReq.setAdvMessage(false);
        sendReq.setAuto(false);
        sendReq.setDone(false);
        sendReq.setTargetCreateDone(false);
        sendReq.setAlrimTalk(false);

        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setNotificationGroup(sendReq);
        notificationGroupRepository.save(notificationGroup);
    }

    //알림대상 등록
    private void saveNotificationTarget(MustadNotificationReq sendReq, String targetName, Long partnerSeq){
        NotificationTarget notificationTarget = new NotificationTarget(sendReq);
        notificationTarget.setPartnerSeq(partnerSeq);
        notificationTarget.setName(targetName);
        notificationTargetRepository.save(notificationTarget);
    }


    /**
     * 프로바이더 키
     *
     * @param type 분루
     * @return String
     */
    private String getProviderKey(String type) {
        String key = "";

        switch (type) {
            case "google":
                key = "accounts.google.com";
                break;
            case "facebook":
                key = "graph.facebook.com";
                break;
            case "apple":
                key = "appleid.apple.com";
                break;
            default:
                key = FEDERATEDAUTH_PARAM + mustadConfig.getUserpoolId();
        }

        return key;
    }


    /** http 요청 */
    private HttpResponse getHttpResponse(AbstractHttpMessage httpMethod) throws IOException {
        HttpResponse response;
        if(httpMethod instanceof HttpPost) { // post
            response = httpClient.execute((HttpPost) httpMethod);

        } else if(httpMethod instanceof HttpGet){ // get
            response = httpClient.execute((HttpGet) httpMethod);

        } else if(httpMethod instanceof HttpPut) { // put
            response = httpClient.execute((HttpPut) httpMethod);

        } else if(httpMethod instanceof HttpDelete) { // delete
            response = httpClient.execute((HttpDelete) httpMethod);

        } else {
            response = httpClient.execute((HttpGet) httpMethod); // default
        }
        return response;
    }

    public Map getLoginMobileType(HttpServletRequest request) {

        Map map = new HashMap();
        String userAgent = request.getHeader("User-Agent");
        Boolean loginType = userAgent.indexOf(IS_MOBILE) > -1 ? true : false;

        String loginMobileType="";

        if(loginType) {

            if (userAgent.contains("ANDROID")) {
                loginMobileType = "ANDROID";
            }else if ((userAgent.indexOf("IPHONE") > -1) || (userAgent.indexOf("IPAD") > -1)) {
                loginMobileType = "IOS";
            }else {
                loginMobileType = "ETC";
            }
        }

        map.put("loginType", loginType);
        map.put("loginMobileType",loginMobileType);
        return map;
    }

}

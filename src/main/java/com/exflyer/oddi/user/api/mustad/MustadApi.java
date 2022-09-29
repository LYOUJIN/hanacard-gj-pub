package com.exflyer.oddi.user.api.mustad;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.mustad.dto.AwsKakaoAuthReq;
import com.exflyer.oddi.user.api.mustad.dto.AwsKakaoAuthRes;
import com.exflyer.oddi.user.api.mustad.dto.FederatedAuthRes;
import com.exflyer.oddi.user.api.mustad.dto.KakaoAccessTokenInfo;
import com.exflyer.oddi.user.api.mustad.dto.MemeberAdvStateResult;
import com.exflyer.oddi.user.api.mustad.dto.MustadKakaoRes;
import com.exflyer.oddi.user.api.mustad.dto.MustadResult;
import com.exflyer.oddi.user.api.mustad.dto.MustadToken;
import com.exflyer.oddi.user.api.mustad.dto.MustadVerificationNumberReq;
import com.exflyer.oddi.user.api.mustad.dto.MustadNotificationReq;
import com.exflyer.oddi.user.api.mustad.service.MustadService;
import com.exflyer.oddi.user.api.user.account.service.MemberAccountService;
import com.exflyer.oddi.user.api.user.auth.dto.CertificationResult;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "머스타드", protocols = "http")
@Slf4j
@RestController
public class MustadApi {

    @Autowired
    private MustadService mustadService;

    @Autowired
    private MemberAccountService memberAccountService;

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ApiOperation(value = "카카오 토큰 API", notes = "카카오 토큰 API 입니다. ")
    @PostMapping(path = "/mustad/kakao/oauth/token/{code}")
    public ApiResponseDto<MustadKakaoRes> goMustadKakaoToken(@PathVariable String code) throws ApiException, Exception {
        MustadKakaoRes result = mustadService.goMustadKakaoToken(code);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "카카오 토큰정보 API", notes = "카카오 토큰정보 API 입니다. ")
    @PostMapping(path = "/mustad/kakao/user/token/{accessTokenInfo}")
    public ApiResponseDto<KakaoAccessTokenInfo> goMustadKakaoAccessTokenInfo(@PathVariable String accessToken) throws ApiException, Exception {
        log.debug("kakao Token : " + accessToken);
        KakaoAccessTokenInfo result = mustadService.goMustadKakaoAccessTokenInfo(accessToken);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "카카오 로그아웃 API", notes = "카카오 로그아웃 API 입니다. ")
    @PostMapping(path = "/mustad/kakao/user/logout/{accessToken}")
    public ApiResponseDto<KakaoAccessTokenInfo> goMustadKakaoLogout(@PathVariable String accessToken) throws ApiException, Exception {
        KakaoAccessTokenInfo result = mustadService.goMustadKakaoLogout(accessToken);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "AWS_KAKAO_Auth API", notes = "AWS_KAKAO_Auth API 입니다. ")
    @PostMapping(path = "/mustad/kakao/auth/token")
    public ApiResponseDto<AwsKakaoAuthRes> goMustadAuthorize(@Validated @RequestBody AwsKakaoAuthReq awsKakaoAuthReq) throws ApiException, Exception {
        AwsKakaoAuthRes result = mustadService.goMustadAuthorize(awsKakaoAuthReq);
        log.debug("====================================================");
        log.debug("result : {} ", result);
        log.debug("====================================================");
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }


    @ApiOperation(value = "머스타드 Cognito 토큰으로 서비스 사용자 토큰 조회 API", notes = "Cognito 토큰으로 서비스 사용자 토큰 조회 API ")
    @LoginNeedApi
    @PostMapping(path = "/mustad/federated/token")
    public ApiResponseDto<FederatedAuthRes> goFederatedAuth(@Validated @RequestBody MustadToken mustadToken) throws ApiException, Exception {
        FederatedAuthRes result = mustadService.goFederatedAuth(mustadToken.getType(), mustadToken.getToken());
        log.debug("====================================================");
        log.debug("result : {} ", result);
        log.debug("====================================================");
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "머스타드 리스트 조회 API", notes = "머스타드 리스트 조회 API 입니다. ")
    @LoginNeedApi
    @PutMapping(path = "/mustad/content/{token}")
    public ApiResponseDto<MustadResult> goMustadContentList(
        @PathVariable String token) throws Exception {
        MustadResult result = mustadService.goMustadContentList(token);
        log.debug("====================================================");
        log.debug("result : {} ", result);
        log.debug("====================================================");
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    /*******************[ 2022.08.09 머스타드 연동 API 개발 중 ] ********************************
     * 1. 머스타드 회원가입 API -> 회원가입 후 머스타드 API 호출
     * 2. 머스타드 사용자 정보 변경 API
     * 3. 머스타드 대기/광고 상태 조회 API
     * 4. 머스타드 사용자 탈퇴 API
     * 5. 머스타드 인증번호 발송 API
     * @return
     * @throws ApiException
     * @throws Exception
     ****************************************************************************************/
    @ApiOperation(value = "머스타드 자동 로그인 API", notes = "머스타드 자동 로그인 API 입니다. ")
    @PostMapping(path = "/mustad/signin")
    public ApiResponseDto signin(@RequestBody MustadToken mustadToken,HttpServletRequest request) throws ApiException, Exception {
        CertificationResult certificationResult = mustadService.signin(mustadToken.getToken(),request);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, certificationResult);
    }

    @ApiOperation(value = "머스타드 사용자 변경 API", notes = "머스타드 사용자 변경 API 입니다. ")
    @PutMapping(path = "/mustad/modify/user")
    public ApiResponseDto<Map> modifyMustadMember(@RequestHeader String token) throws Exception {
        Map result = mustadService.modifyMustadMember(token);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS,result);
    }

    @ApiOperation(value = "머스타드 대기/광고 상태 조회 API", notes = "머스타드 대기/광고 상태 조회 API 입니다. ")
    @GetMapping(path = "/mustad/adv-state")
    public ApiResponseDto<MemeberAdvStateResult> findMustadAdvState(@RequestHeader String mustadId) throws Exception {
        MemeberAdvStateResult result = mustadService.findMustadAdvState(mustadId);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "머스타드 사용자 회원탈퇴 API", notes = "머스타드 사용자 회원탈퇴 API 입니다. ")
    @PutMapping(path = "/mustad/resign-user")
    public ApiResponseDto<Map> modifyMustadResignUser(@RequestHeader String mustadId) throws Exception {
        Map result = mustadService.modifyMustadResignUser(mustadId);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

    @ApiOperation(value = "머스타드 인증 번호 발송 ", notes = "전화 번호로 인증번호를 발송 하는 API 입니다.")
    @PostMapping(path = "/mustad/verification-number")
    public ApiResponseDto<String> sendVerificationNumber(@RequestBody MustadVerificationNumberReq req) throws Exception {
        mustadService.sendVerificationNumber(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }

    @ApiOperation(value = "머스타드 문자발송 ", notes = "전화 번호로 문자발송 하는 API 입니다.")
    @PostMapping(path = "/mustad/message")
    public ApiResponseDto<String> sendMessage(@RequestBody MustadNotificationReq req) throws ApiException {
        mustadService.sendMessage(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }
}


package com.exflyer.oddi.user.api.user.auth.api;

import com.exflyer.oddi.user.annotaions.OddiEncrypt;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccount;
import com.exflyer.oddi.user.api.user.auth.dto.PasswordReq;
import com.exflyer.oddi.user.api.user.auth.dto.CertificationResult;
import com.exflyer.oddi.user.api.user.auth.dto.SignInReq;
import com.exflyer.oddi.user.api.user.auth.service.SignInService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "로그인", protocols = "http")
@Slf4j
@RestController
public class SignInApi {

  @Autowired
  private SignInService signInService;

  public static final String IS_MOBILE = "MOBILE";

  @InitBinder
  public void InitBinder(WebDataBinder dataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @ApiOperation(value = "로그인", notes = "로그인 API 입니다. ")
  @PostMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
  @OddiEncrypt
  public ApiResponseDto signIn(@Validated @RequestBody SignInReq signInReq, HttpServletRequest request) throws ApiException{

    String userAgent = request.getHeader("User-Agent").toUpperCase();
    signInReq.setLoginType(userAgent.indexOf(IS_MOBILE) > -1 ? true : false);

    if(signInReq.isLoginType()) {

      String loginMobileType = userAgent.toUpperCase();

      if (userAgent.indexOf("ANDROID") > -1) {
        loginMobileType = "ANDROID";
      }else if ((userAgent.indexOf("IPHONE") > -1) || (userAgent.indexOf("IPAD") > -1)) {
        loginMobileType = "IOS";
      }else {
        loginMobileType = "ETC";
      }
      signInReq.setLoginMobileType(loginMobileType);
    }

    CertificationResult certificationResult = signInService.signIn(signInReq,"oddi");
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, certificationResult);
  }

  @ApiOperation(value = "비밀번호 찾기 ", notes = "비밀번호 찾기 API")
  @PostMapping(path = "/password")
  @OddiEncrypt
  public ApiResponseDto findPassword(@Validated @RequestBody PasswordReq passwordReq) throws ApiException,IOException {
    signInService.findPassword(passwordReq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }
}

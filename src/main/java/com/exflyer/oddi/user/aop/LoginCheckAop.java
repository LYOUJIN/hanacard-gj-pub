package com.exflyer.oddi.user.aop;


import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.jwt.dto.JwtVerifyResult;
import com.exflyer.oddi.user.jwt.service.JwtService;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoginCheckAop {

  @Autowired
  private JwtService jwtService;

  @Around("@annotation(com.exflyer.oddi.user.annotaions.LoginNeedApi)")
  public Object validateAdmin(ProceedingJoinPoint joinPoint) throws Throwable {

    // token 추출
    JwtVerifyResult jwtVerifyResult = extractAccessToken();

    Object[] args = createAdminAuthArgs(joinPoint, new MemberAuth(jwtVerifyResult.getId()));

    return joinPoint.proceed(args);
  }

  private HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  private JwtVerifyResult extractAccessToken() throws ApiException {
    HttpServletRequest request = getHttpServletRequest();
    String accessTokenHeader = request.getHeader("x-access-token");
    validationHeader(accessTokenHeader);
    return jwtService.verify(accessTokenHeader);
  }

  private void validationHeader(String accessTokenHeader) throws ApiException {
    if (StringUtils.isBlank(accessTokenHeader)) {
      log.info(">>>> accessTokenHeader blank");
      throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
    }
  }

  private Object[] createAdminAuthArgs(ProceedingJoinPoint joinPoint, MemberAuth memberAuth) {

    return Arrays.stream(joinPoint.getArgs())
      .map(
        data -> {
          if (data instanceof MemberAuth) {
            data = memberAuth;
          }
          return data;
        })
      .toArray();
  }
}

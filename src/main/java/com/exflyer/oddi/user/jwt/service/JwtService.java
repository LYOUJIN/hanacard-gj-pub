package com.exflyer.oddi.user.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exflyer.oddi.user.api.user.auth.dao.LoginConfigMapper;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.jwt.dto.JwtResult;
import com.exflyer.oddi.user.jwt.dto.JwtVerifyResult;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.OddiLoginConfig;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtService {

  private static final String SECRET_KEY_ADMIN = "api-user";
  private static final String ADMIN_ISSUER = "api-user";
  private static final String ID_KEY = "id";

  @Autowired
  private LoginConfigMapper loginConfigMapper;

  public JwtResult createAccessToken(String id, int expireDayCount, boolean passwordReset, String provider) {
    OddiLoginConfig oddiLoginConfig = loginConfigMapper.find();
    ZonedDateTime zonedDateTime = createExpireDateTime(oddiLoginConfig, expireDayCount);
    String accessToken =  createAccessToken(id, zonedDateTime);
    String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
    return new JwtResult(accessToken, refreshToken, passwordReset,provider);
  }

  private ZonedDateTime createExpireDateTime(OddiLoginConfig oddiLoginConfig, int expireDayCount) {
    Integer expireMin = oddiLoginConfig.getManagerTokenExpiredMin();
    if (expireDayCount > 0) {
      expireMin = expireDayCount * 24 * 60;
    }
    LocalDateTime expiredDateTime = LocalDateUtils.krNow().plusMinutes(expireMin);
    return expiredDateTime.atZone(ZoneId.systemDefault());
  }


  private String createAccessToken(String encryptMemberId, ZonedDateTime zonedDateTime) {
    try {
      return JWT.create()
        .withIssuer(ADMIN_ISSUER)
        .withClaim(ID_KEY, encryptMemberId)
        .withExpiresAt(Date.from(zonedDateTime.toInstant()))
        .sign(Algorithm.HMAC256(SECRET_KEY_ADMIN));
    } catch (JWTCreationException exception) {
      return null;
    }
  }

  public JwtVerifyResult verify(String accessToken) throws ApiException {
    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY_ADMIN)).withIssuer(ADMIN_ISSUER).build();
      DecodedJWT decodedJWT = verifier.verify(accessToken);
      return new JwtVerifyResult(decodedJWT.getClaim(ID_KEY).asString());
    } catch (TokenExpiredException te) {
      log.error("#### token expired ", te);
      throw new ApiException(ApiResponseCodes.TOKEN_EXPIRED);
    } catch (Exception e) {
      log.error("#### token verify exception ", e);
      throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
    }
  }

}

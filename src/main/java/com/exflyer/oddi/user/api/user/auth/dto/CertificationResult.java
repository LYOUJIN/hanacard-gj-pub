package com.exflyer.oddi.user.api.user.auth.dto;

import com.exflyer.oddi.user.jwt.dto.JwtResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CertificationResult {

  @ApiModelProperty(value = "accessToken", position = 0)
  private String accessToken;

  @ApiModelProperty(value = "refressToken", position = 1)
  private String refreshToken;

  @ApiModelProperty(value = "passwordReset", position = 2)
  private boolean passwordReset;

  @ApiModelProperty(value = "passwordCode", position = 3)
  private String code;

  @ApiModelProperty(value = "passwordMsg", position = 4)
  private String message;

  @ApiModelProperty(value = "provider", position = 4)
  private String provider;

  public CertificationResult(JwtResult jwtResult) {
    this.accessToken = jwtResult.getAccessToken();
    this.refreshToken = jwtResult.getRefreshToken();
    this.passwordReset = jwtResult.isPasswordReset();
    this.code = jwtResult.getCode();
    this.message = jwtResult.getMessage();
    this.provider = jwtResult.getProvider();
  }
}

package com.exflyer.oddi.user.jwt.dto;

import lombok.Data;

@Data
public class JwtResult {

  private String accessToken;

  private String refreshToken;

  private boolean passwordReset;

  private String code;

  private String message;

  private String provider;

  public JwtResult(String accessToken, String refreshToken, boolean passwordReset, String provider) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.passwordReset = passwordReset;
    this.provider = provider;
  }
}

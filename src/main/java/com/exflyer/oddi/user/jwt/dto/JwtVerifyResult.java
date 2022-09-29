package com.exflyer.oddi.user.jwt.dto;

import lombok.Data;

@Data
public class JwtVerifyResult {

  private String id;

  private String name;

  public JwtVerifyResult(String id) {
    this.id = id;
  }

  public JwtVerifyResult(String id, String name) {
    this.id = id;
    this.name = name;
  }
}

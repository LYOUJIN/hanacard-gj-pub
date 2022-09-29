package com.exflyer.oddi.user.api.user.auth.dto;

import lombok.Data;

@Data
public class TwoFactorResult {

  private boolean done;

  private String certNumber;

  public TwoFactorResult(String certNumber) {
    this.done = true;
    this.certNumber = certNumber;
  }
}

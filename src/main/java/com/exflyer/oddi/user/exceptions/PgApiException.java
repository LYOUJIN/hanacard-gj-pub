package com.exflyer.oddi.user.exceptions;

import com.exflyer.oddi.user.enums.PgApiCode;
import lombok.Data;

@Data
public class PgApiException extends Exception{

  private int status;

  private String code;

  private String message;

  public PgApiException(PgApiCode pgApiCode) {
    this.status = pgApiCode.getStatus();
    this.code = pgApiCode.getCode();
    this.message = pgApiCode.getMessage();
  }

}

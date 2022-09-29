package com.exflyer.oddi.user.exceptions;


import com.exflyer.oddi.user.enums.ApiResponseCodes;
import lombok.Getter;

public class ApiException extends Exception {

  @Getter
  private ApiResponseCodes apiResponseCodes;


  public ApiException(ApiResponseCodes code){
    this.apiResponseCodes = code;
  }

}

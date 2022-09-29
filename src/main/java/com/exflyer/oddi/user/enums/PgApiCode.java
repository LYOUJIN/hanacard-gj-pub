package com.exflyer.oddi.user.enums;


import lombok.Getter;

public enum PgApiCode {

  OK(200,"00", "OK"),
  PG_ERROR(500,"01", "PG연동 중 에러가 발생하였습니다."),
  EPISODE_ERROR(500,"02", "에피소드내 에러가 발생하였습니다.."),
  ERROR(400, "03", "잘못된 요청 입니다."),
  NOT_FOUND_PAYMENTINFO(404, "04", "결재 정보를 확인 할 수 없습니다."),
  NOT_FOUND_PAYMENTUSER(404, "05", "결재 요청 사용자 정보를 확인 할 수 없습니다.")
  ;

  @Getter
  private int status;

  @Getter
  private String code;

  @Getter
  private String message;


  PgApiCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}

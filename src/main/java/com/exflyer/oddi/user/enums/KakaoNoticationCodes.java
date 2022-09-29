package com.exflyer.oddi.user.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;


@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum KakaoNoticationCodes {

  USER_PW_CHANGE("mustad_pw_find", "비밀번호 재설정"),
  ADV_CANCEL("mustad_ad_refund", "광고 취소 및 환불")

  ;


  private String code;

  private String name;

  KakaoNoticationCodes(String code, String name) {
    this.code = code;
    this.name = name;
  }
}

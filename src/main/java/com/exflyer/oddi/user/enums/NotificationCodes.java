package com.exflyer.oddi.user.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;


@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum NotificationCodes {

    MANAGER_NAME("", "오디 관리자")
    , MUSTAD_MANAGER_NAME("", "머스타드 발송")
    , MUSTAD_MANAGER_ID("", "mustad")
  ;


  private String code;

  private String name;

  NotificationCodes(String code, String name) {
    this.code = code;
    this.name = name;
  }
}

package com.exflyer.oddi.user.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;


@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum ChannelCodes {

  ODDI_ZONE("PTT001", "오디존"),
  SUBWAY("PTT002", "지하철");


  private String code;

  private String name;

  ChannelCodes(String code, String name) {
    this.code = code;
    this.name = name;
  }
}

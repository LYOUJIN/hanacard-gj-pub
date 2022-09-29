package com.exflyer.oddi.user.enums;

import lombok.Getter;

@Getter
public enum PagingOrderEnums {

  REG_DATE("reg_date"),
  MODE_DATE("mod_date"),
  ;


  private String orderName;

  PagingOrderEnums(String oderingName) {
    this.orderName = oderingName;
  }
}

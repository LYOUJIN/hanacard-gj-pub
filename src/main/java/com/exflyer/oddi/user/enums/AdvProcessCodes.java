package com.exflyer.oddi.user.enums;

import lombok.Getter;

public enum AdvProcessCodes {

  ADV_WAITING("ADT001", 1, "대기"),
  ADV_APPROVAL("ADT002", 2, "승인"),
  ADV_REJECTION("ADT003", 3, "보류"),
  PROGRESS_RECEIPT("PGT001", 4, "신청"),
  PROGRESS_PAYMENT_COMPLETE("PGT002", 5, "결제완료"),
  PROGRESS_PAYMENT_FAIL("PGT003", 6, "결제실패"),
  PROGRESS_PAYMENT_CANCEL("PGT004", 7, "결제취소"),
  PROGRESS_RECEIPT_CANCEL("PGT005", 8, "신청취소")
  ;


  @Getter
  private final String code;

  @Getter
  private final Integer reqCode;

  @Getter
  private final String message;


  AdvProcessCodes(String code, Integer reqCode, String message) {
    this.code = code;
    this.reqCode = reqCode;
    this.message = message;

  }
}

package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InicisMobileReqResult {

  @ApiModelProperty(value = " 결과코드 [\"00\": 정상, 이외 실패]", position = 0)
  private String P_STATUS;

  @ApiModelProperty(value = "결과메시지", position = 0)
  private String P_RMESG1;

  @ApiModelProperty(value = "인증거래번호", position = 0)
  private String P_TID;

  @ApiModelProperty(value = "거래금액", position = 0)
  private String P_AMT;

  @ApiModelProperty(value = "승인요청 URL", position = 0)
  private String P_REQ_URL;

  @ApiModelProperty(value = "가맹점 임의 데이터", position = 0)
  private String P_NOTI;

  @ApiModelProperty(value = "결제 순번", position = 0)
  private Long paymentSeq;


  @ApiModelProperty(value = "상점아이디", position = 0)
  private String mid;
}

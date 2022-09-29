package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InicisReqResult {

  @ApiModelProperty(value = "결과코드 [\"0000\": 정상, 이외 실패]", position = 0)
  private String resultCode;

  @ApiModelProperty(value = "결과메시지", position = 0)
  private String resultMsg;

  @ApiModelProperty(value = "상점아이디", position = 0)
  private String mid;

  @ApiModelProperty(value = "주문번호", position = 0)
  private String orderNumber;

  @ApiModelProperty(value = "승인요청 검증 토큰", position = 0)
  private String authToken;

  @ApiModelProperty(value = "순승인요청 Url번", position = 0)
  private String authUrl;

  @ApiModelProperty(value = "망취소요청 Url", position = 0)
  private String netCancelUrl;

  @ApiModelProperty(value = "인증결과 인코딩 [Default: UTF-8]", position = 0)
  private String charset;

  @ApiModelProperty(value = "가맹점 임의 데이터", position = 0)
  private String merchantData;

  @ApiModelProperty(hidden = true)
  private Long paymentSeq;

  @ApiModelProperty(hidden = true)
  private String signature;

  @ApiModelProperty(hidden = true)
  private String timestamp;
}

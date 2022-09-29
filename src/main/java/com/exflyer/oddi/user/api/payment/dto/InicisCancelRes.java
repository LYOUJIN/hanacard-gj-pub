package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import lombok.Data;

@Data
public class InicisCancelRes {

  @ApiModelProperty(value= "resultCode")
  private String resultCode;

  @ApiModelProperty(value= "resultMsg")
  private String resultMsg;

  @ApiModelProperty(value= "취소일자 [YYYYMMDD]")
  private String cancelDate;

  @ApiModelProperty(value= "\t취소시간 [hhmmss]")
  private String cancelTime;

  @ApiModelProperty(value= "현금영수증 취소승인번호")
  private String cshrCancelNum;

  @ApiModelProperty(value= "취소실패 응답시 상세코드")
  private String detailResultCode;

  @ApiModelProperty(value= "특정 가맹점 전용 응답필드")
  private String receiptInfo;


}


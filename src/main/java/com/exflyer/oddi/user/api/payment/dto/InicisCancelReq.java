package com.exflyer.oddi.user.api.payment.dto;

import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModelProperty;
import java.net.InetAddress;
import lombok.Data;

@Data
public class InicisCancelReq {

  @ApiModelProperty(value= "\"Refund\" 고정")
  private String type;

  @ApiModelProperty(value= "지불수단 코드")
  private String paymethod;

  @ApiModelProperty(value= "전문생성시간 [YYYYMMDDhhmmss]")
  private String timestamp;

  @ApiModelProperty(value= "가맹점 요청 서버IP (추후 거래 확인 등에 사용됨)")
  private String clientIp;

  @ApiModelProperty(value= "상점아이디")
  private String mid;

  @ApiModelProperty(value= "취소요청 승인TID")
  private String tid;

  @ApiModelProperty(value= "취소요청사유")
  private String msg;

  @ApiModelProperty(value= "전문위변조 HASH")
  private String hashData;

  public InicisCancelReq(PaymentCancelRes req,String clientIp) {
    this.type = "Refund";
    this.paymethod = req.getPayMethod();
    this.timestamp = LocalDateUtils.krNowByFormatter("yyyyMMddHHmmss");
    this.clientIp = clientIp;
    this.mid = req.getMid();
    this.tid = req.getTid();
    this.msg = "결제취소";
    this.hashData = req.getHashData();
  }

}


package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentCancelRes {

    @ApiModelProperty(value = "상점아이디")
    private String mid;

    @ApiModelProperty(value = "결제순번")
    private Long paymentSeq;

    @ApiModelProperty(value = "광고순번")
    private Long advSeq;

    @ApiModelProperty(value = "광고명")
    private String advTitle;

    @ApiModelProperty(value= "회원id")
    private String memberId;

    @ApiModelProperty(value= "쿠폰번호")
    private String couponNumber;

    @ApiModelProperty(value = "취소요청 승인TID")
    private String tid;

    @ApiModelProperty(value= "지불수단")
    private String payMethod;

    @ApiModelProperty(value = "전문위변조 HASH")
    private String hashData;

    @ApiModelProperty(value = "금액")
    private Integer price;

    @ApiModelProperty(value = "구매자명")
    private String buyerName;

    @ApiModelProperty(value = "구매자이메일")
    private String buyerEmail;

    @ApiModelProperty(value = "이메일")
    private String custEmail;

    @ApiModelProperty(value = "전화번호")
    private String buyerTel;

    @ApiModelProperty(value = "승인번호")
    private String applNum;

    @ApiModelProperty(value= "광고 진행상태 코드")
    private String type;
}


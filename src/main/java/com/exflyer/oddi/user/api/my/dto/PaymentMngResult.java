package com.exflyer.oddi.user.api.my.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PaymentMngResult extends PagingSearch {

    @ApiModelProperty(value = "결제순번", position = 0)
    private Long paymentSeq;

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "묶음순번", position = 0)
    private Long productSeq;

    @ApiModelProperty(value = "신청일", position = 0)
    private String regDate;

    @ApiModelProperty(value = "주문번호", position = 0)
    private String moid;

    @ApiModelProperty(value = "결제일", position = 0)
    private String applDate;

    @ApiModelProperty(value = "광고종류코드", position = 0)
    private String channelType;

    @ApiModelProperty(value = "광고종류", position = 0)
    private String channelTypeName;

    @ApiModelProperty(value = "광고제목", position = 0)
    private String advName;

    @ApiModelProperty(value = "광고업종 코드", position = 0)
    private String businessTypeCode;

    @ApiModelProperty(value = "광고업종", position = 0)
    private String businessTypeName;

    @ApiModelProperty(value = "신청시작기간", position = 0)
    private String advStartDate;

    @ApiModelProperty(value = "신청종료기간", position = 0)
    private String advEndDate;

    @ApiModelProperty(value = "쿠폰사용", position = 0)
    private String couponName;

    @ApiModelProperty(value = "지불수단", position = 0)
    private String payMethod;

    @ApiModelProperty(value = "광고금액", position = 0)
    private String advPrice;

    @ApiModelProperty(value = "할인금액", position = 0)
    private String discountPrice;

    @ApiModelProperty(value = "실 결제금액", position = 0)
    private String price;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountType;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountTypeName;

    @ApiModelProperty(value = "결제성공여부", position = 0)
    private String resultCode;
    
    @ApiModelProperty(value = "결제성공여부메시지", position = 0)
    private String resultMsg;

    @ApiModelProperty(value = "결제상태(0이면 결제완료, 1이면 결제취소)", position = 0)
    private String pgPaymentType;

    @ApiModelProperty(value = "광고슬롯", position = 0)
    private Integer requestSlot;

    @ApiModelProperty(value = "결제일/결제취소일", position = 0)
    private String payDate;

    @ApiModelProperty(value = "위치", position = 0)
    private List<OddiMngPartner> partnerList;


}

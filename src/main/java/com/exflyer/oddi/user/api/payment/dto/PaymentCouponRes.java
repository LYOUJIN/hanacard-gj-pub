package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class PaymentCouponRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "프로모션쿠폰 매핑 seq", position = 0)
    private Long couponMappingSeq;

    @ApiModelProperty(value = "프로모션쿠폰 seq", position = 0)
    private Long couponSeq;

    @ApiModelProperty(value = "쿠폰이름", position = 0)
    private String couponName;

    @ApiModelProperty(value = "할인금액", position = 0)
    private Integer discountPrice;

    @ApiModelProperty(value = "쿠폰코드", position = 0)
    private String couponCode;

    @ApiModelProperty(value = "선착순여부", position = 0)
    private Boolean couponLimit;

    @ApiModelProperty(value = "선착순여부", position = 0)
    private String couponLimitName;

    @ApiModelProperty(value = "공통쿠폰여부", position = 0)
    private Boolean multiUsing;

    @ApiModelProperty(value = "공통쿠폰여부", position = 0)
    private String multiUsingName;

    @ApiModelProperty(value = "쿠폰만료일자", position = 0)
    private String expiredDate;

    @ApiModelProperty(value = "가입시인지 첫결제인지", position = 0)
    private String signupCouponType;

    @ApiModelProperty(value = "가입시인지 첫결제인지", position = 0)
    private String signupCouponTypeNmae;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountType;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountTypeName;
}

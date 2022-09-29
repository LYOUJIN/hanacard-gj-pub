package com.exflyer.oddi.user.api.promotion.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionCouponResult {

    @ApiModelProperty(value = "순번", position = 0)
    private Long promotionCouponSeq;

    @ApiModelProperty(value = "할인금액", position = 0)
    private Integer discountPrice;

    @ApiModelProperty(value = "만료날짜", position = 0)
    private String expiredDate;

    @ApiModelProperty(value = "쿠폰코드", position = 0)
    private String couponCode;
}

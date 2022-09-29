package com.exflyer.oddi.user.api.my.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyPromotionResult {

    @ApiModelProperty(value = "프로모션 쿠폰 매핑 순번", position = 0)
    private Long couponMappingSeq;

    @ApiModelProperty(value = "프로모션 쿠폰 순번", position = 0)
    private Long couponSeq;

    @ApiModelProperty(value = "사용여부", position = 0)
    private String usable;

    @ApiModelProperty(value = "사용여부명", position = 0)
    private String usableName;

    @ApiModelProperty(value = "쿠폰번호", position = 0)
    private String couponCode;

    @ApiModelProperty(value = "쿠폰명", position = 0)
    private String name;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountType;

    @ApiModelProperty(value = "할인 종류(정액, 정률)", position = 0)
    private String discountTypeName;

    @ApiModelProperty(value = "할인금액", position = 0)
    private String discountPrice;

    @ApiModelProperty(value = "프로모션채널타입(전체/오디존/지하철)", position = 0)
    private String promotionChannelType;

    @ApiModelProperty(value = "프로모션채널타입(전체/오디존/지하철)", position = 0)
    private String promotionChannelTypeName;

    @ApiModelProperty(value = "시작날짜", position = 0)
    private String startDate;

    @ApiModelProperty(value = "만료날짜", position = 0)
    private String expiredDate;

    @ApiModelProperty(value = "사용날짜", position = 0)
    private String usingDate;

    @ApiModelProperty(value = "결제순번", position = 0)
    private Long paymentSeq;

    @ApiModelProperty(value = "사용가능 여부 (Y or N)", position = 0)
    private String usableYn;

    @ApiModelProperty(value = "프로모션 명칭", position = 0)
    private String promotionName;
}

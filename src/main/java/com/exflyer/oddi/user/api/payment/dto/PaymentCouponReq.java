package com.exflyer.oddi.user.api.payment.dto;

import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class PaymentCouponReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "회원id", position = 0)
    private String memberId;

    @ApiModelProperty(value = "현재일자", position = 0)
    private String expiredDate;

    @ApiModelProperty(value = "쿠폰사용여부", position = 0)
    private boolean couponUsable;

    @ApiModelProperty(value = "사용여부", position = 0)
    private boolean promotionUsable;

    @ApiModelProperty(value = "첫결제여부", position = 0)
    private int paymentFirst;

    @ApiModelProperty(value = "가입자 쿠폰 종류 구분(첫결제시,가입시)", hidden = true)
    private String signupCouponType;

    @ApiModelProperty(value = "쿠폰매핑seq", position = 0)
    private Long couponMappingSeq;

    @ApiModelProperty(value = "프로모션채널종류", position = 0)
    private String promotionChannelType;

    public PaymentCouponReq(String memberId, String channelType) {
        this.memberId = memberId;
        this.expiredDate = LocalDateUtils.dateConvertFormatter();
        this.couponUsable = false;
        this.promotionUsable = true;
        this.promotionChannelType = channelType;
    }


}

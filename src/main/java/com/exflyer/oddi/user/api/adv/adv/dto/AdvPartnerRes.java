package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvPartnerRes {

    @ApiModelProperty(value = "순번", position = 0)
    private Long partnerSeq;

    @ApiModelProperty(value = "매장이름", position = 1)
    private String mallName;

    @ApiModelProperty(value = "총 슬롯", position = 2)
    private Integer totalSlot;

    @ApiModelProperty(value = "슬롯당 금액", position = 4)
    private Integer slotPrice;

}
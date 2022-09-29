package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvProductPartnerRes {

    @ApiModelProperty(value = "순번", position = 0)
    private Long partnerSeq;

    @ApiModelProperty(value = "매장이름", position = 1)
    private String mallName;

    @ApiModelProperty(value = "총 슬롯", position = 2)
    private Integer totalSlot;

    @ApiModelProperty(value = "슬롯당 금액", position = 4)
    private Integer slotPrice;

    @ApiModelProperty(value = "묶음순번", position = 5)
    private Long productSeq;

    @ApiModelProperty(value = "묶음이름", position = 6)
    private String productName;

}
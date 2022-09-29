package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvPartnerDetailRes {

    @ApiModelProperty(value = "순번", position = 0)
    private Long partnerSeq;

    @ApiModelProperty(value = "광고순번", position = 1)
    private Long advSeq;

    @ApiModelProperty(value = "매장이름", position = 2)
    private String mallName;

    @ApiModelProperty(value = "총 슬롯", position = 3)
    private Integer totalSlot;

    @ApiModelProperty(value = "슬롯당 금액", position = 4)
    private Integer slotPrice;

    @ApiModelProperty(value = "요청슬롯수", position = 5)
    private Integer requestSlot;


}
package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvPartnerReq {

    @ApiModelProperty(hidden = true)
    private Long advSeq;

    @ApiModelProperty(value="파트너순번", position = 0)
    private Long partnerSeq;

    @ApiModelProperty(value="요청슬롯", position = 0)
    private Integer requestSlot;

    @ApiModelProperty(value = "시작 날짜", hidden = true)
    private String startDate;

    @ApiModelProperty(value = "종료 날짜", hidden = true)
    private String endDate;

    @ApiModelProperty(value = "파트너별 금액", hidden = true)
    private Integer price;

}

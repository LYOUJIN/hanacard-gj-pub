package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OddiHistoryPartner {

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "묶음광고 순번", position = 0)
    private String productSeq;

    @ApiModelProperty(value = "매장 순번", position = 0)
    private String partnerSeq;

    @ApiModelProperty(value = "매장이름", position = 0)
    private String mallName;

}

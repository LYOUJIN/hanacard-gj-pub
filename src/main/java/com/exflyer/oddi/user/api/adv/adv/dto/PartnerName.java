package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartnerName {

    @ApiModelProperty(value = "Seq")
    private long seq;

    @ApiModelProperty(value = "매장명,지하철명")
    private String name;

    @ApiModelProperty(value = "묶음상품여부")
    private boolean isProductYn;

    @ApiModelProperty(value = "지하철 라인")
    private String SubwayCode;

}

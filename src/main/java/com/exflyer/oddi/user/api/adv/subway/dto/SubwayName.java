package com.exflyer.oddi.user.api.adv.subway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubwayName {

    @ApiModelProperty(value = "Seq")
    private long seq;

    @ApiModelProperty(value = "지하철명")
    private String name;

    @ApiModelProperty(value = "뱃지")
    private String badgeCode;

    @ApiModelProperty(value = "지하철 라인")
    private String SubwayCode;

}

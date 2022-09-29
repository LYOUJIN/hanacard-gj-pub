package com.exflyer.oddi.user.api.adv.subway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubwayInfoList {

    @ApiModelProperty(value = "지하철 이름 리스트")
    private List<SubwayName> subwayNameList;

    @ApiModelProperty(value = "지하철 호선 리스트")
    private List<SubwayLine> subwayLineList;

}

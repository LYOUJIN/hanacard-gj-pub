package com.exflyer.oddi.user.api.adv.subway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubwayImage {

    @ApiModelProperty(value = "파일Seq")
    private long seq;

    @ApiModelProperty(value = "파일경로")
    private String path;

    @ApiModelProperty(value = "실제파일명")
    private String name;

}

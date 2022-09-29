package com.exflyer.oddi.user.api.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HomeMainPartners {

    @ApiModelProperty(value = "매장명")
    private String mallName;

    @ApiModelProperty(value = "간단설명")
    private String summary;

    @ApiModelProperty(value = "파일경로")
    private String filePath;

}

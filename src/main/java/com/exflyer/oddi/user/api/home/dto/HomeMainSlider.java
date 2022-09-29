package com.exflyer.oddi.user.api.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HomeMainSlider {

    @ApiModelProperty(value = "슬라이더명")
    private String name;

    @ApiModelProperty(value = "설명")
    private String description;

    @ApiModelProperty(value = "버튼명")
    private String buttonName;

    @ApiModelProperty(value = "버튼링크")
    private String routerLink;

    @ApiModelProperty(value = "파일경로")
    private String filePath;

}

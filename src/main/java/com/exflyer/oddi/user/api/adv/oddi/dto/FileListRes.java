package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileListRes {

    @ApiModelProperty(value = "순번", position = 0)
    private Long seq;

    @ApiModelProperty(value = "파일seq", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "파일경로", position = 0)
    private String path;

    @ApiModelProperty(value = "파일명이름", position = 0)
    private String name;

    @ApiModelProperty(value = "파일확장자", position = 0)
    private String extension;

}

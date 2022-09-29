package com.exflyer.oddi.user.api.voc.terms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermsServiceRes {

    @ApiModelProperty(value = "약관버전", position = 0)
    private String version;

    @ApiModelProperty(value = "순번", position = 0)
    private Long seq;

    @ApiModelProperty(value = "약관제목", position = 0)
    private String title;

    @ApiModelProperty(value = "약관내용", position = 0)
    private String contents;

    @ApiModelProperty(value = "필수여부", position = 0)
    private Boolean required;

    @ApiModelProperty(value = "코드", position = 0)
    private String code;

    @ApiModelProperty(value = "코드명", position = 0)
    private String name;


}

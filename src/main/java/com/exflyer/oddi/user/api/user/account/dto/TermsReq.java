package com.exflyer.oddi.user.api.user.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermsReq {

    @ApiModelProperty(value = "동의약관 seq", position = 7)
    private Long termsSeq;

    @ApiModelProperty(value = "동의여부", position = 7)
    private Boolean termsAgree = false;

}

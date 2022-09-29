package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AwsKakaoAuthReq {

    @ApiModelProperty(value = "토큰", position = 0)
    private String userAccessToken;

    @ApiModelProperty(value = "사용자 이메일", position = 0)
    private String userEmail;

    @ApiModelProperty(value = "사용자이름", position = 0)
    private String userName;

}

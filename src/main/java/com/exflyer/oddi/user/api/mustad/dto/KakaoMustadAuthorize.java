package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KakaoMustadAuthorize {

    @ApiModelProperty(value = "토큰", position = 7)
    private String userAccessToken;

    @ApiModelProperty(value = "사용자 이메일", position = 7)
    private String userEmail;

    @ApiModelProperty(value = "사용자이름", position = 7)
    private String userName;

    public KakaoMustadAuthorize(AwsKakaoAuthReq awsKakaoAuthReq) {
        this.userAccessToken = awsKakaoAuthReq.getUserAccessToken();
        this.userEmail = awsKakaoAuthReq.getUserEmail();
        this.userName = awsKakaoAuthReq.getUserName();
    }
}

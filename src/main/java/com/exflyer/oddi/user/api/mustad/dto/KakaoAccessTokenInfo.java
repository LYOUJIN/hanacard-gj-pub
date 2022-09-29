package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KakaoAccessTokenInfo {

    @ApiModelProperty(value = "결과코드", position = 7)
    private String resultCode;

    @ApiModelProperty(value = "결과값", position = 7)
    private String resultMessage;

    @ApiModelProperty(value = "회원번호", position = 7)
    private String id;

    @ApiModelProperty(value = "액세스 토큰 만료 시간(초)", position = 7)
    private String expires_in;

    @ApiModelProperty(value = "토큰이 발급된 앱 ID", position = 7)
    private String app_id;

}

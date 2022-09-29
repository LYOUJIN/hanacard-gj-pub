package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MustadKakaoRes {


    @ApiModelProperty(value = "결과코드", position = 7)
    private String resultCode;

    @ApiModelProperty(value = "결과값", position = 7)
    private String resultMessage;


    @ApiModelProperty(value = "에러코드", position = 7)
    private String error_code;

    @ApiModelProperty(value = "결과코드", position = 7)
    private String error;

    @ApiModelProperty(value = "결과에러메시지", position = 7)
    private String error_description;

    @ApiModelProperty(value = "토큰 타입, bearer로 고정", position = 7)
    private String token_type;

    @ApiModelProperty(value = "사용자 액세스 토큰 값", position = 7)
    private String access_token;

    @ApiModelProperty(value = "액세스 토큰 만료 시간(초)", position = 7)
    private String expires_in;

    @ApiModelProperty(value = "사용자 리프레시 토큰 값", position = 7)
    private String refresh_token;

    @ApiModelProperty(value = "리프레시 토큰 만료 시간(초)", position = 7)
    private String refresh_token_expires_in;

}

package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AwsKakaoAuthRes {

    @ApiModelProperty(value = "결과코드", position = 0)
    private String resultCode;

    @ApiModelProperty(value = "결과값", position = 0)
    private String resultMessage;

    @ApiModelProperty(value = "AccessToken", position = 0)
    private String AccessToken;

    @ApiModelProperty(value = "TokenType", position = 0)
    private String TokenType;

    @ApiModelProperty(value = "RefreshToken", position = 0)
    private String RefreshToken;
    
    @ApiModelProperty(value = "IdToken", position = 0)
    private String IdToken;

    @ApiModelProperty(value = "사용자토큰으로 변환한 값(federatedAuth)", position = 0)
    private FederatedAuthRes federatedAuth;





}

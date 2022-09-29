package com.exflyer.oddi.user.api.mustad.dto;

import com.exflyer.oddi.user.config.KakaoAuthorizeConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KakaoToken {

    @ApiModelProperty(value = "authorization_code로 고정", position = 7)
    private String grant_type;

    @ApiModelProperty(value = "앱 REST API 키", position = 7)
    private String client_id;

    @ApiModelProperty(value = "인가 코드가 리다이렉트된 URI", position = 7)
    private String redirect_uri;

    @ApiModelProperty(value = "인가 코드 받기 요청으로 얻은 인가 코드", position = 7)
    private String code;

    public KakaoToken(String code, KakaoAuthorizeConfig config) {
        this.grant_type = config.getGrantType();
        this.client_id = config.getKey();
        this.redirect_uri = config.getRedirectUri();
        this.code = code;
    }
}

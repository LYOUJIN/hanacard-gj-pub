package com.exflyer.oddi.user.api.mustad.dto;

import com.exflyer.oddi.user.config.KakaoAuthorizeConfig;
import io.swagger.annotations.ApiModelProperty;

public class KakaoAuthorize {

    @ApiModelProperty(value = "앱 REST API 키", position = 7)
    private String client_id;

    @ApiModelProperty(value = "인가 코드가 리다이렉트된 URI", position = 7)
    private String redirect_uri;

    @ApiModelProperty(value = "code", position = 7)
    private String response_type;

    @ApiModelProperty(value = "code", position = 7)
    private String state;

    public KakaoAuthorize(KakaoAuthorizeConfig config) {
        this.client_id = config.getKey();
        this.redirect_uri = config.getRedirectUri() + "/mustad/kakao/oauth/token";
        this.response_type = "code";
        this.state = "oddi";
    }
}

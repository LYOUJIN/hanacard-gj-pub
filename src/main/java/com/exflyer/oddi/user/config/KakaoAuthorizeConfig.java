package com.exflyer.oddi.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class KakaoAuthorizeConfig {

    @Value("${mustad.kakao.state}")
    private String state;

    @Value("${mustad.kakao.type}")
    private String type;

    @Value("${mustad.kakao.key}")
    private String key;

    @Value("${mustad.kakao.oauth-host}")
    private String oauthHost;

    @Value("${mustad.kakao.api-host}")
    private String apiHost;

    @Value("${mustad.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${mustad.kakao.oauth-authorize}")
    private String oauthAuthorize;

    @Value("${mustad.kakao.token-uri}")
    private String tokenUri;

    @Value("${mustad.kakao.grant_type}")
    private String grantType;

    @Value("${mustad.kakao.aws.auth}")
    private String kakaoAwsAuth;


}

package com.exflyer.oddi.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class MustadConfig {

    @Value("${mustad.federated.auth}")
    private String federatedAuth;

    @Value("${mustad.federated.my-content}")
    private String myContent;

    @Value("${mustad.federated.userpool-id}")
    private String userpoolId;

    @Value("${mustad.federated.client-id}")
    private String clientId;

    @Value("${mustad.storeprofiles}")
    private String storeprofiles;

    @Value("${mustad.userprofile}")
    private String userprofile;

    @Value("${mustad.signin}")
    private String mustadSigninUrl;
}

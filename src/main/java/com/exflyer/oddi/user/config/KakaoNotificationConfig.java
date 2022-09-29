package com.exflyer.oddi.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KakaoNotificationConfig {

    @Value("${kakaoNotification.apiUrl}")
    private String apiUrl;

    @Value("${kakaoNotification.id}")
    private String id;

    @Value("${kakaoNotification.pw}")
    private String pw;

    @Value("${kakaoNotification.deptCode}")
    private String deptCode;

    @Value("${kakaoNotification.sendProfileUUID}")
    private String sendProfileUUID;

    @Value("${kakaoNotification.sendProfileKey}")
    private String sendProfileKey;

    @Value("${kakaoNotification.reSendFromPhoneNum}")
    private String reSendFromPhoneNum;

    @Value("${kakaoNotification.reSendFromName}")
    private String reSendFromName;

    @Value("${kakaoNotification.reSendFromId}")
    private String reSendFromId;

    @Value("${kakaoNotification.templateCode}")
    private String templateCode;



}

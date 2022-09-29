package com.exflyer.oddi.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class InicisConfig {

    @Value("${inicis.mid}")
    private String inicisMid;

    @Value("${inicis.sign.key}")
    private String inicisSignKey;

    @Value("${inicis.cancel.url}")
    private String inicisCancelUrl;

    @Value("${inicis.key}")
    private String inicisKey;

    @Value("${inicis.site-url}")
    private String inicisSiteUrl;

    @Value("${inicis.clientIp}")
    private String inicisClientIpl;
}

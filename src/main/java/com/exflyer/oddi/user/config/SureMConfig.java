package com.exflyer.oddi.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SureMConfig {

  @Value("${surem.host}")
  private String host;

  @Value("${surem.deptcode}")
  private String deptcode;

  @Value("${surem.usercode}")
  private String usercode;

  @Value("${surem.sender}")
  private String sender;

  private final String SMS_PATH = "/sms/v1/json";
  private final String LMS_PATH = "/lms/v1/json";
  private final String SEND_RESULT_PATH = "/recv";
}

package com.exflyer.oddi.user.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

  // 기본은 카멜
  @Bean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public Gson gsonUnderScore() {
    return  new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }
}

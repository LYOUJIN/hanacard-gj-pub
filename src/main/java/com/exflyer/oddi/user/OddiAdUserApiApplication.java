package com.exflyer.oddi.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class OddiAdUserApiApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {

    SpringApplication.run(OddiAdUserApiApplication.class, args);
    log.info("start!! user-api");
  }

}

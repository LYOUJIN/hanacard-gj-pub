package com.exflyer.oddi.user.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JasyptConfig {

  @Bean
  public StringEncryptor jasyptStringEncryptor() {
    String key = "oddiad";
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(key); // 암호화할 때 사용하는 키
    config.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC"); // 암호화 알고리즘
    config.setProvider(new BouncyCastleProvider());
    encryptor.setConfig(config);
    return encryptor;
  }
}

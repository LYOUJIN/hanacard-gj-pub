package com.exflyer.oddi.user.share;


import com.exflyer.oddi.user.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AesEncryptor aesEncryptor;

  public static final String Header = "x-access-token";


  public String getTestToken(){
    String testId = "test11@test.com";
    return jwtService.createAccessToken(aesEncryptor.encrypt(testId), 0, true,"oddi").getAccessToken();
  }


}

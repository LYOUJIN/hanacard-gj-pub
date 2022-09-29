package com.exflyer.oddi.user.api.user.auth.service;


import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

  @Autowired
  private PassWordEncrypt passWordEncrypt;

  public boolean validPassword(String password, String encryptPassword) throws ApiException {
    try {
      return passWordEncrypt.checkPassword(password, encryptPassword);
    } catch (Exception e) {
      throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
    }
  }
}

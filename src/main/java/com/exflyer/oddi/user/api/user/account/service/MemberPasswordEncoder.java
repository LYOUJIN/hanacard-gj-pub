package com.exflyer.oddi.user.api.user.account.service;

import org.jasypt.util.password.PasswordEncryptor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class MemberPasswordEncoder implements PasswordEncryptor {

  @Override
  public String encryptPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public boolean checkPassword(String plainPassword, String encryptedPassword) {
    return BCrypt.checkpw(plainPassword, encryptedPassword);
  }
}

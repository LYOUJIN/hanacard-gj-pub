package com.exflyer.oddi.user.api.user.account.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MemberAccountServiceTest {

  @Autowired
  private MemberAccountService memberAccountService;

  @DisplayName("전화번호 인증 여부")
  @Test
  public void isConfirmPhoneNumber() throws Exception {
    boolean isConfirm = memberAccountService.isConfirmPhoneNumberAuth("01051227638");
    log.info("{}", isConfirm);
  }

  @DisplayName("전화번호 인증 번호 발송")
  @Test
  public void sendVerificationNumber() throws Exception {
    memberAccountService.sendVerificationNumber("01051227638");
  }

}

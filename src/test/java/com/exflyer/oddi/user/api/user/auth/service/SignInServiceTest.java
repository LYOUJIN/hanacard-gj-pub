package com.exflyer.oddi.user.api.user.auth.service;

import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SignInServiceTest {


  @Autowired
  private KakaoNotificationService kakaoNotificationService;

  @Autowired
  private MemberRepository memberRepository;
  
  
  @DisplayName("비밀번호 초기화 알림톡")
  @Test
  public void sendInitPassword() throws Exception {
    Optional<Member> byId = memberRepository.findById("1cDSXyPFpGuRIecShbKAWNhpNwKJjIHaGUMdau8dbkU=");
    kakaoNotificationService.sendKakaoPassword(byId.get(), "123456");
  }
}

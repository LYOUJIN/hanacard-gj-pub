package com.exflyer.oddi.user.api.user.auth.service;


import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.api.user.auth.dao.LoginConfigMapper;
import com.exflyer.oddi.user.api.user.auth.dto.CertificationResult;
import com.exflyer.oddi.user.api.user.auth.dto.PasswordReq;
import com.exflyer.oddi.user.api.user.auth.dto.SignInReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.jwt.dto.JwtResult;
import com.exflyer.oddi.user.jwt.service.JwtService;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SignInService {

  @Autowired
  private PasswordService passwordService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private LoginConfigMapper loginConfigMapper;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PassWordEncrypt passWordEncrypt;

  @Autowired
  private KakaoNotificationService kakaoNotificationService;

  private final int PASSWORD_CHANGED_LIMIT_DAY = 180;

  public CertificationResult signIn(SignInReq signInReq, String provider) throws ApiException {

    Member member = loginConfigMapper.findAllByMember(signInReq.getEmail(), provider);

    if (member == null) {
      throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
    }

    member.setLoginType(signInReq.isLoginType());
    member.setLoginMobileType(signInReq.getLoginMobileType());

    if ("CTS002".equals(member.getStateCode())) {
      throw new ApiException(ApiResponseCodes.USER_CTS002);
    }

    if ("CTS003".equals(member.getStateCode())) {
      throw new ApiException(ApiResponseCodes.USER_CTS003);
    }

    if ("CTS004".equals(member.getStateCode())) {
      throw new ApiException(ApiResponseCodes.USER_CTS004);
    }

    boolean isValidPassword = passwordService.validPassword(signInReq.getPassword(), member.getPassword());
    if (!isValidPassword) {
      member.setPasswordErrorCount(member.getPasswordErrorCount() + 1);

      memberRepository.save(member);
      throw new ApiException(ApiResponseCodes.AUTHENTIFICATION);
    }

    Map passwordChange = checkPasswordChangedDate(member);

    int expiredDayCount = 0;
    if (signInReq.isAutoLogin()) {
      expiredDayCount = 30;
    }

    JwtResult jwtResult = jwtService.createAccessToken(member.getId(), expiredDayCount, member.getPasswordReset(), provider);

    jwtResult.setCode(String.valueOf(passwordChange.get("code")));
    jwtResult.setMessage(String.valueOf(passwordChange.get("msg")));
    member.setPasswordErrorCount(0);
    member.setLoginDate(LocalDateUtils.krNow());
    memberRepository.save(member);
    return new CertificationResult(jwtResult);
  }

  public Map checkPasswordChangedDate(Member member) throws ApiException {

    Map map = new HashMap();
    LocalDateTime nowDateTime = LocalDateUtils.krNow();
    long changedPasswordDuration = Duration.between(member.getPasswordModDate(), nowDateTime)
        .toDays();
    if (changedPasswordDuration >= PASSWORD_CHANGED_LIMIT_DAY) {
      map.put("code",ApiResponseCodes.PASSWORD_CHANGE_DAY_OVER.getCode());
      map.put("msg",ApiResponseCodes.PASSWORD_CHANGE_DAY_OVER.getMessage());
    }
    return map;
  }

  /*public void checkPasswordChangedDate(Member member) throws ApiException {
    if (member.getPasswordModDate() == null) {
      return;
    }
    LocalDateTime nowDateTime = LocalDateUtils.krNow();
    long changedPasswordDuration = Duration.between(member.getPasswordModDate(), nowDateTime)
      .toDays();
    if (changedPasswordDuration >= PASSWORD_CHANGED_LIMIT_DAY) {
      throw new ApiException(ApiResponseCodes.PASSWORD_CHANGE_DAY_OVER);
    }

  }*/

  private Member findMemberBySignReq(String email) throws ApiException {
    Optional<Member> memberOptional = memberRepository.findById(email);
    return memberOptional
      .orElseThrow(() -> new ApiException(ApiResponseCodes.AUTHENTIFICATION));
  }

  @Transactional
  public void findPassword(PasswordReq passwordReq) throws ApiException {

    //1.회원정보가 존재하는지 확인
    Member member = memberRepository.findByIdEmail(passwordReq.getEmail(), passwordReq.getPhoneNumber());
    if (member == null) {
      throw new ApiException(ApiResponseCodes.NOT_FOUND);
    }

    //비밀번호 초기화
    String initPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8) + "!";
    member.setPassword(passWordEncrypt.encryptPassword(initPassword));
    member.setPasswordModDate(LocalDateUtils.krNow());
    member.setPasswordReset(false);
    memberRepository.save(member);

    //알림톡 보내기
    kakaoNotificationService.sendKakaoPassword(member, initPassword);
  }


}

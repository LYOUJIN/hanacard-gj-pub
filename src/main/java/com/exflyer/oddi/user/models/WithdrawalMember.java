package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.api.user.account.dto.MemberAddReq;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 탈퇴 회원 정보
 */
@Data
@Entity
@ApiModel("탈퇴 회원 정보")
@Table(name = "withdrawal_member")
@NoArgsConstructor
public class WithdrawalMember implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @Id
  @ApiModelProperty("id")
  @Column(name = "id", nullable = false)
  private String id;

  /**
   * 이름
   */
  @ApiModelProperty("이름")
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * 비밀번호
   */
  @ApiModelProperty("비밀번호")
  @Column(name = "password", nullable = false)
  private String password;

  /**
   * 전화번호
   */
  @ApiModelProperty("전화번호")
  @Column(name = "phone_number")
  private String phoneNumber;

  /**
   * 수신 동의
   */
  @ApiModelProperty("수신 동의")
  @Column(name = "receive_consent", nullable = false)
  private boolean receiveConsent = false;

  /**
   * 인증 코드
   */
  @ApiModelProperty("인증 코드")
  @Column(name = "auth_code")
  private String authCode;

  /**
   * 가입 날짜
   */
  @ApiModelProperty("가입 날짜")
  @Column(name = "signup_date", nullable = false)
  private LocalDateTime signupDate;

  /**
   * 변경 ID
   */
  @ApiModelProperty("변경 ID")
  @Column(name = "mod_id")
  private String modId;

  /**
   * 변경 날짜
   */
  @ApiModelProperty("변경 날짜")
  @Column(name = "mod_date", nullable = false)
  private LocalDateTime modDate;

  /**
   * 상태코드
   */
  @ApiModelProperty("상태코드 (CTS001 : 정상 CTS002 : 정지 CTS003 : 비밀번호오류)")
  @Column(name = "state_code", nullable = false)
  private String stateCode;

  /**
   * 비밀번호 오류 카운트
   */
  @ApiModelProperty("비밀번호 오류 카운트")
  @Column(name = "password_error_count")
  private Integer passwordErrorCount;

  /**
   * 비밀번호 변경 날짜
   */
  @ApiModelProperty("비밀번호 변경 날짜")
  @Column(name = "password_mod_date")
  private LocalDateTime passwordModDate;


  /**
   * 로그인 날짜
   */
  @ApiModelProperty("로그인 날짜")
  @Column(name = "login_date")
  private LocalDateTime loginDate;

  /**
   * 메모
   */
  @Column(name = "memo")
  @ApiModelProperty("메모")
  private String memo;

  /**
   * 개인구분
   */
  @Column(name = "member_gbn")
  @ApiModelProperty("개인구분")
  private Boolean memberGbn;

  /**
   * 이메일
   */
  @ApiModelProperty("email")
  @Column(name = "email")
  @DecryptField
  private String email;


  /**
   * 탈퇴일자
   */
  @Column(name = "withdrawal_date")
  @ApiModelProperty("탈퇴일자")
  private LocalDateTime withdrawalDate;

  /**
   * 가입구분
   * 오디,토크니토,카카오 등등
   */
  @ApiModelProperty("오디,토크니토,카카오 등등")
  @Column(name = "provider")
  private String provider;

  /**
   * 머스타드 id
   */
  @ApiModelProperty("머스타드 id")
  @Column(name = "mustad_id")
  private String mustadId;

  public WithdrawalMember(Member member) {
    this.id = member.getId();
    this.name = member.getName();
    this.password = member.getPassword();
    this.phoneNumber = member.getPhoneNumber();
    this.receiveConsent = member.isReceiveConsent();
    this.authCode = member.getAuthCode();
    this.signupDate = member.getSignupDate();
    this.stateCode = "CTS005";
    this.passwordErrorCount = member.getPasswordErrorCount();
    this.passwordModDate = member.getPasswordModDate();
    this.loginDate = member.getLoginDate();
    this.memo = member.getMemo();
    this.memberGbn = member.getMemberGbn();
    this.email = member.getEmail();
    this.withdrawalDate = LocalDateUtils.krNow();
    this.provider = member.getProvider();
    this.mustadId = member.getMustadId();
  }

}

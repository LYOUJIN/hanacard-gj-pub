package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.user.account.dto.VerificationNumberReq;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 전화인증
 */
@Data
@Entity
@ApiModel("전화인증")
@Table(name = "phone_auth")
@NoArgsConstructor
public class PhoneAuth implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 전화번호
   */
  @Id
  @ApiModelProperty("전화번호")
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  /**
   * 인증 번호
   */
  @ApiModelProperty("인증 번호")
  @Column(name = "auth_number")
  private String authNumber;

  /**
   * 인증 만료 시간
   */
  @ApiModelProperty("인증 만료 시간")
  @Column(name = "auth_expired_time")
  private LocalDateTime authExpiredTime;
  /**
   * 확인 여부
   */
  @Column(name = "confirm")
  @ApiModelProperty("확인 여부")
  private boolean confirm;

  public PhoneAuth(VerificationNumberReq verificationNumberReq, AesEncryptor encryptor) {
    this.phoneNumber = encryptor.encrypt(verificationNumberReq.getPhoneNumber());
    this.authNumber = verificationNumberReq.getVerificationNumber();
    this.authExpiredTime = LocalDateUtils.krNow().plusMinutes(3);
    this.confirm = false;
  }
}

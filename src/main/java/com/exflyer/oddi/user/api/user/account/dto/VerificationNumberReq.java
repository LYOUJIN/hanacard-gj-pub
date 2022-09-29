package com.exflyer.oddi.user.api.user.account.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.annotaions.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationNumberReq {

  @NotBlank
  @ApiModelProperty(value = "휴대폰번호", position = 0, required = true)
  @EncryptField
  @PhoneNumber
  private String phoneNumber;

  @ApiModelProperty(value = "인증번호", position = 1)
  @NotBlank
  private String verificationNumber;

  public VerificationNumberReq(String phoneNumber, String randomNumeric) {
    this.phoneNumber = phoneNumber;
    this.verificationNumber = randomNumeric;
  }
}

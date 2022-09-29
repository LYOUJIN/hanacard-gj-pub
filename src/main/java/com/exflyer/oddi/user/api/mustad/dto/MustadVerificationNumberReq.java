package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MustadVerificationNumberReq {

  @NotBlank
  @ApiModelProperty(value = "전화번호", position = 0)
  private String phoneNumber;

  @NotBlank
  @ApiModelProperty(value = "인증번호", position = 1)
  private String verificationNumber;
}

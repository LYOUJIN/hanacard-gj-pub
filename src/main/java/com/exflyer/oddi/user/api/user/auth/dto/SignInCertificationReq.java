package com.exflyer.oddi.user.api.user.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInCertificationReq {

  @NotBlank
  @ApiModelProperty(value = "관리자 ID", position = 0)
  private String id;

  @NotBlank
  @ApiModelProperty(value = "인증번호", position = 0)
  private String certificationNumber;

}

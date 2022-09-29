package com.exflyer.oddi.user.api.user.auth.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.annotaions.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordReq {

  @ApiModelProperty(value = "이메일(ID)", position = 1)
  @NotBlank
  @EncryptField
  @Email
  private String email;

  @ApiModelProperty(value = "전화번호", position = 3)
  @NotBlank
  @EncryptField
  @PhoneNumber
  private String phoneNumber;

}

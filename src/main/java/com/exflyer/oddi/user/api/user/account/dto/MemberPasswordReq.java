package com.exflyer.oddi.user.api.user.account.dto;

import com.exflyer.oddi.user.annotaions.Password;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberPasswordReq {

  @ApiModelProperty(value = "id", hidden = true)
  private String id;

  @ApiModelProperty(value = "비밀번호", position = 2)
  @NotBlank
  @Password
  private String password;

  @ApiModelProperty(value = "변경 비밀번호", position = 2)
  @NotBlank
  @Password
  private String newPassword;

  @ApiModelProperty(value = "변경 비밀번호확인", position = 2)
  @NotBlank
  @Password
  private String newChangePassword;

}

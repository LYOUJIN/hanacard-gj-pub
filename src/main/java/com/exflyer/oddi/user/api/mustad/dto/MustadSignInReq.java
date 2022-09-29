package com.exflyer.oddi.user.api.mustad.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MustadSignInReq {

  @NotBlank
  @ApiModelProperty(value = "이메일", position = 0)
  private String email;

  @ApiModelProperty(value = "비밀번호", position = 1, hidden = true)
  private String password;

  @ApiModelProperty(value = "로그인 (웹:0,모바일:1)", position = 3, hidden = true)
  private boolean loginType = false;

  @ApiModelProperty(value = "모바일종류(ios,android)", position = 4, hidden = true)
  private String loginMobileType;
}

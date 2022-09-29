package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MustadMessageReq {

  @NotBlank
  @ApiModelProperty(value = "전화번호", position = 0)
  private String phoneNumber;

  @NotBlank
  @ApiModelProperty(value = "text", position = 1)
  private String text;
}

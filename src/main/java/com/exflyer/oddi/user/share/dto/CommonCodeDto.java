package com.exflyer.oddi.user.share.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class CommonCodeDto {

  @ApiModelProperty(value = "코드", position = 0)
  private String code;

  @ApiModelProperty(value = "value", position = 0)
  private String value;

  @ApiModelProperty(value = "타입", position = 1)
  private String type;

  @ApiModelProperty(value = "이름", position = 2)
  private String name;

  @ApiModelProperty(value = "순서", position = 3)
  private String ordering;

  public void setCode(String code) {
    this.code = code;
    if (!StringUtils.isBlank(code)) {
      this.value = code;
    }
  }
}

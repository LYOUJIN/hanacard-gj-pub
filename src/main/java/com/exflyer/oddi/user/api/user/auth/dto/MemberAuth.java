package com.exflyer.oddi.user.api.user.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberAuth {

  @ApiModelProperty(hidden = true)
  private String id;

  public MemberAuth(String id) {
    this.id = id;
  }

}

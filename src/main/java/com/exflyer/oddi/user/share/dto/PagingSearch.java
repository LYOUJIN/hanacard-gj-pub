package com.exflyer.oddi.user.share.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import lombok.Data;

@Data
public class PagingSearch {


  @ApiModelProperty(value = "페이지 번호", position = 1, required = true)
  private int pageNo = 1;

  @Max(value = 1000)
  @ApiModelProperty(value = "페이지 row size", position = 2, required = true)
  private int pageSize = 10;

  @ApiModelProperty(value = "정렬 문자열", position = 3)
  private String orderBy;
}

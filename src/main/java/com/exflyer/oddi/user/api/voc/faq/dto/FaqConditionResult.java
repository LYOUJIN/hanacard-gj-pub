package com.exflyer.oddi.user.api.voc.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FaqConditionResult {

  @ApiModelProperty(value = "제목", position = 0)
  private String title;

  @ApiModelProperty(value = "내용", position = 1)
  private String contents;

  @ApiModelProperty(value = "분류", position = 3)
  private String categoryCode;

  @ApiModelProperty(value = "등록날짜", position = 4)
  private String regDate;

  @ApiModelProperty(value = "순번", position = 5)
  private String seq;
}

package com.exflyer.oddi.user.api.voc.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeConditionRes {

  @ApiModelProperty(value = "번호", position = 0)
  private String rownum;

  @ApiModelProperty(value = "순번", position = 0)
  private String seq;

  @ApiModelProperty(value = "제목", position = 1)
  private String title;

  @ApiModelProperty(value = "등록날짜", position = 2)
  private String regDate;

}

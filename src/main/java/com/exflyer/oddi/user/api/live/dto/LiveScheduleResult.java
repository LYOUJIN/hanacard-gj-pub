package com.exflyer.oddi.user.api.live.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LiveScheduleResult {

  @ApiModelProperty(value = "제목", position = 1)
  private String title;

  @ApiModelProperty(value = "운영날짜", position = 2)
  private String operationDay;

  @ApiModelProperty(value = "시작시간", position = 3)
  private String startTime;

  @ApiModelProperty(value = "종료시간", position = 4)
  private String endTime;

}

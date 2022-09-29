package com.exflyer.oddi.user.api.my.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyAlarmResult {

    @ApiModelProperty(value = "마이페이지 알람여부")
    private Boolean mypageAlarm = false;

    @ApiModelProperty(value = "오디관리 알람여부")
    private Boolean oddiMngAlarm = false;

    @ApiModelProperty(value = "1:1문의 알람여부")
    private Boolean inquireAlarm = false;
}

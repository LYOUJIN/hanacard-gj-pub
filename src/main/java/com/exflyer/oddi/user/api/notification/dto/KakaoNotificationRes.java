package com.exflyer.oddi.user.api.notification.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class KakaoNotificationRes {

    @ApiModelProperty(value = "호출에 따른 결과코드", position = 1, required = true)
    private String code;

    @ApiModelProperty(value = "코드값 상세설명", position = 2, required = true)
    private String message;

    @ApiModelProperty(value = "정상 요청일 경우 요청건의 전송결과", position = 3)
    private List results;

    @ApiModelProperty(value = "비정상 요청에서 추가정보 필요시", position = 4)
    private String additional_information;
}

package com.exflyer.oddi.user.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvUserCancelReq {

    @ApiModelProperty(value = "고객ID", position = 0)
    private String id;

    @ApiModelProperty(value = "고객이름", position = 0)
    private String name;

    @ApiModelProperty(value = "광고이름", position = 0)
    private String title;

    @ApiModelProperty(value = "신청일시", position = 0)
    private String regDate;

    @ApiModelProperty(value = "광고시작일", position = 0)
    private String startDate;

    @ApiModelProperty(value = "광고종료일", position = 0)
    private String endDate;

    @ApiModelProperty(value = "환불금액", position = 0)
    private String price;

    @ApiModelProperty(value = "전화번호", position = 0)
    private String phoneNumber;



}

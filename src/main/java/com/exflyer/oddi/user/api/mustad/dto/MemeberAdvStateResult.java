package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @user : 2022-08-11
 * @test
 */
@Data
public class MemeberAdvStateResult {

    @ApiModelProperty(value = "대기상태", position = 7)
    private Integer ready;

    @ApiModelProperty(value = "광고상태", position = 7)
    private Integer ad;

}

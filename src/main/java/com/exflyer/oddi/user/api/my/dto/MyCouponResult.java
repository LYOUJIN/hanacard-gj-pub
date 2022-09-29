package com.exflyer.oddi.user.api.my.dto;

import com.exflyer.oddi.user.enums.ApiResponseCodes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyCouponResult {

    @ApiModelProperty(value = "코드", position = 1)
    private ApiResponseCodes code;

    @ApiModelProperty(value = "메세지", position = 2)
    private String message;

}

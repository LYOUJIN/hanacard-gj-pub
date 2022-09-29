package com.exflyer.oddi.user.api.adv.partner.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PartnerInfo {

    @ApiModelProperty(value = "매장이름", position = 0)
    private String mallName;

    @ApiModelProperty(value = "이미지경로", position = 0)
    private String imagePath;
}

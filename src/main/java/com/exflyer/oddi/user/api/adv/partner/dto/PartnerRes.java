package com.exflyer.oddi.user.api.adv.partner.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PartnerRes {

    @ApiModelProperty(value = "구이름", position = 0)
    private String guName;

    @ApiModelProperty(value = "매장리스트", position = 0)
    private List<PartnerInfo> partnerInfos;

}

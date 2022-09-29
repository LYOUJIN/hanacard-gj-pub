package com.exflyer.oddi.user.api.adv.partner.dto;

import com.exflyer.oddi.user.models.Code;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PartnerBusinessCode {

    @ApiModelProperty(value = "사업자코드 조회")
    private List<Code> businessCode;

}

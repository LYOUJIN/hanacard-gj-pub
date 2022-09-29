package com.exflyer.oddi.user.api.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class HomeMainRes {

    @ApiModelProperty(value = "매인슬라이더")
    private List<HomeMainSlider> sliders;

    @ApiModelProperty(value = "매인 파트너스")
    private List<HomeMainPartners> partners;

    @ApiModelProperty(value = "파트너 건수")
    private Integer partnerCnt;

}

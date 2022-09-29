package com.exflyer.oddi.user.api.my.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiMngPartner {

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "묶음광고 순번", position = 0)
    private String productSeq;

    @ApiModelProperty(value = "매장 순번", position = 0)
    private String partnerSeq;

    @ApiModelProperty(value = "매장이름", position = 0)
    private String mallName;

    @ApiModelProperty(value = "광고 슬롯", position = 0)
    private Integer requestSlot;


}

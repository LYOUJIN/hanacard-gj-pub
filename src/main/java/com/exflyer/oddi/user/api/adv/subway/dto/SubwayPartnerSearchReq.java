package com.exflyer.oddi.user.api.adv.subway.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubwayPartnerSearchReq {

    @ApiModelProperty(value="지하철명", position = 0)
    private String subwayName;

    @ApiModelProperty(value="지하철호선", position = 0)
    private String subwayCode;

    @ApiModelProperty(value="지하철(PTT002)", position = 0, hidden = true)
    private String channelType;

    @ApiModelProperty(value="환승 여부", position = 0)
    private boolean isTransfer;

    @ApiModelProperty(value="묶음상품 여부", position = 0, required = true)
    private boolean isProduct;

}

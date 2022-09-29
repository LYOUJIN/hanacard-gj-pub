package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class OddiReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "운영여부", hidden = true)
    private boolean operation;

    @ApiModelProperty(value = "광고/지하철", hidden = true)
    private String channelType;

    @ApiModelProperty(value = "검색어", position = 0)
    private String searchText;
}

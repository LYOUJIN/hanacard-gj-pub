package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class AdvSearchReq {

    @ApiModelProperty(value="채널타입", hidden = true)
    private String channelType;

    @ApiModelProperty(value="순번", position = 0)
    private List<String> seqs;

    @ApiModelProperty(hidden = true)
    private String regId;
}

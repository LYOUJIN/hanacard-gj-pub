package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class MustadResult {

    @ApiModelProperty(value = "결과코드", position = 7)
    private String resultCode;

    @ApiModelProperty(value = "결과값", position = 7)
    private String resultMessage;

    @ApiModelProperty(value = "머스타드토큰", position = 7)
    private String mustadToken;

    private List<ProviderMetadata> providerMetadata;

}

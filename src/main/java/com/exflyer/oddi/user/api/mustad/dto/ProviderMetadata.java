package com.exflyer.oddi.user.api.mustad.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Data;

@Data
public class ProviderMetadata {

    @ApiModelProperty(value = "이미지정보", hidden = true)
    private ProviderMetadata providerMetadata;

    private String id;

    private String contentType;

    private String lastModified;

    private String orientation;

    private String ownerId;

    private String name;

    private String boardType;

    @ApiModelProperty(value = "이미지url", position = 0)
    private String resource;

    private String frameUrl;

    private String linkedContents;

}

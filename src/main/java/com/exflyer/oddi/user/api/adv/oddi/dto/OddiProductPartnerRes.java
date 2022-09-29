package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OddiProductPartnerRes {

    @ApiModelProperty(value = "상품순번", position = 0)
    private String productSeq;

    @ApiModelProperty(value = "파트너순번", position = 0)
    private String partnerSeq;

    @ApiModelProperty(value = "매장 이름", position = 0)
    private String mallName;

    @ApiModelProperty(value = "요약", position = 0)
    private String summary;

    @ApiModelProperty(value = "위도", position = 0)
    private String latitude;

    @ApiModelProperty(value = "경도", position = 0)
    private String longitude;

    @ApiModelProperty(value = "그리드 x", position = 0)
    private Integer gridX;

    @ApiModelProperty(value = "그리드 y", position = 0)
    private Integer gridY;

    @ApiModelProperty(value = "파일순번", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "파일경로", position = 0)
    private String path;

    @ApiModelProperty(value = "파일명", position = 0)
    private String name;

    @ApiModelProperty(value = "확장자", position = 0)
    private String extension;
}

package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartnerFiles {

    /**
     * 채널 종류
     */
    @ApiModelProperty("채널 종류")
    private String channelType;

    /**
     * 기본 광고 파일 순번
     */
    @ApiModelProperty("기본 광고 파일 순번")
    private Long defaultAdvFileSeq;

    /**
     * 기본광고 파일타입
     */
    @ApiModelProperty("기본광고 파일타입")
    private String defaultAdvType;

    /**
     * 노출순서
     */
    @ApiModelProperty("노출순서")
    private Integer view_order;


}

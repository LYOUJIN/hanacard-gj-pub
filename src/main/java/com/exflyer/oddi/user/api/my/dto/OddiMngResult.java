package com.exflyer.oddi.user.api.my.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiMngResult {

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "광고종류코드", position = 0)
    private String channelType;

    @ApiModelProperty(value = "광고종류", position = 0)
    private String channelTypeName;

    @ApiModelProperty(value = "제목", position = 0)
    private String title;

    @ApiModelProperty(value = "신청시작기간", position = 0)
    private String startDate;

    @ApiModelProperty(value = "신청종료기간", position = 0)
    private String endDate;

    @ApiModelProperty(value = "심사코드", position = 0)
    private String auditCode;

    @ApiModelProperty(value = "심사코드명", position = 0)
    private String auditCodeName;

    @ApiModelProperty(value = "묶음광고순번", position = 0)
    private Long productSeq;

    @ApiModelProperty(value = "광고신청일자", position = 0)
    private String regDate;

    @ApiModelProperty(value = "광고프로세스코드", position = 0)
    private String progressCode;

    @ApiModelProperty(value = "광고 신청취소 가능일", position = 9)
    private int oddiAdvCancelDate;

    @ApiModelProperty(value = "위치", position = 0)
    private List<OddiMngPartner> partnerList;


}

package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiHistoryResult {

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "광고종류코드", position = 0)
    private String channelType;

    @ApiModelProperty(value = "디자인제작여부", position = 0)
    private Boolean designRequest;

    @ApiModelProperty(value = "광고종류", position = 0)
    private String channelTypeName;

    @ApiModelProperty(value = "제목", position = 0)
    private String title;

    @ApiModelProperty(value = "업종", position = 0)
    private String businessTypeCode;

    @ApiModelProperty(value = "업종명", position = 0)
    private String businessTypeName;

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

    @ApiModelProperty(value = "요청광고슬롯", position = 0)
    private Long requestSlot;

    @ApiModelProperty(value = "광고진행상태", position = 0)
    private String advStatus;

    @ApiModelProperty(value = "광고신청일", position = 0)
    private String regDate;

    @ApiModelProperty(value = "위치", position = 0)
    private List<OddiHistoryPartner> partnerList;
}

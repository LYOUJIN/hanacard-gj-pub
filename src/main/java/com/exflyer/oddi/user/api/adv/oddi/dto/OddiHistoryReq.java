package com.exflyer.oddi.user.api.adv.oddi.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class OddiHistoryReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "광고코드", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "묶음광고코드", position = 0)
    private Long productSeq;

    @ApiModelProperty(value = "회원id", position = 0)
    private String memberId;

    @ApiModelProperty(value = "오늘날짜(YYYYMMDD)", position = 0)
    private String toDay;

}

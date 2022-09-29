package com.exflyer.oddi.user.api.my.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OddiMng  extends PagingSearch {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "심사코드")
    private String auditCode;

    @ApiModelProperty(value = "광고코드", hidden = true)
    private Long advSeq;

    @ApiModelProperty(value = "묶음광고코드", hidden = true)
    private Long productSeq;

    @ApiModelProperty(value = "회원id", hidden = true)
    private String memberId;

}

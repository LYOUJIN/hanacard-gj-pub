package com.exflyer.oddi.user.api.voc.inquire.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InquireListReq extends PagingSearch {

    @ApiModelProperty(value = "문의유형코드", position = 0, hidden = true)
    private String memberId;

}

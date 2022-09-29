package com.exflyer.oddi.user.api.voc.inquire.dto;

import com.exflyer.oddi.user.models.Code;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InquireTypeCode {

    @ApiModelProperty(value = "1:1문의유형코드 조회")
    private List<Code> inquireTypeCode;

}

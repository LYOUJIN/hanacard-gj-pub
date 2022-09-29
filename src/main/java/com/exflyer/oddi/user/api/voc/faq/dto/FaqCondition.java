package com.exflyer.oddi.user.api.voc.faq.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FaqCondition{
    @ApiModelProperty(value = "분류", position = 1)
    private String categoryCode;
}

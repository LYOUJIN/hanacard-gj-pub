package com.exflyer.oddi.user.api.my.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class PaymentMng extends PagingSearch {


    @ApiModelProperty(value = "회원id", hidden = true)
    private String memberId;

}

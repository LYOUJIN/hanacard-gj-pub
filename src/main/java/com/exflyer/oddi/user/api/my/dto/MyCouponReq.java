package com.exflyer.oddi.user.api.my.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyCouponReq  extends PagingSearch {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "사용자id", hidden = true)
    private String memberId;

    @ApiModelProperty(value = "오늘날짜", hidden = true)
    private String today;

    @ApiModelProperty(value = "가입자 쿠폰 종류 구분(첫결제시,가입시)", hidden = true)
    private String signupCouponType;


}

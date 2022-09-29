package com.exflyer.oddi.user.api.promotion;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponReq;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponResult;
import com.exflyer.oddi.user.api.promotion.service.PromotionService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "프로모션 쿠폰(구)", protocols = "http")
@Slf4j
@RestController
public class PromotionApi {
/*
    @Autowired
    private PromotionService promotionService;

    @ApiOperation(value = "쿠폰 직접입력 등록 API", notes = "쿠폰 직접입력 등록 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/promotion/{couponCode}")
    public ApiResponseDto<PromotionCouponResult> findPromotionCoupon(@PathVariable String couponCode, MemberAuth memberAuth) {

        PromotionCouponReq req = new PromotionCouponReq(couponCode, memberAuth.getId());
        PromotionCouponResult promotion = promotionService.findPromotionCoupon(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, promotion);
    }*/


}

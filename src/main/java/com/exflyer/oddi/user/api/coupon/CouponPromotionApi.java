package com.exflyer.oddi.user.api.coupon;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.my.service.MyCouponMngService;
import com.exflyer.oddi.user.api.payment.dto.PaymentCouponRes;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponResult;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.CouponMapping;
import com.exflyer.oddi.user.models.Payment;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "프로모션 쿠폰", protocols = "http")
@Slf4j
@RestController
public class CouponPromotionApi {

    @Autowired
    private CouponPromotionService couponPromotionService;

    @ApiOperation(value = "쿠폰 직접입력 등록 API", notes = "쿠폰 직접입력 등록 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/promotion/{channelType}/{couponCode}")
    public ApiResponseDto saveCouponPromotion(@PathVariable String channelType,@PathVariable String couponCode, MemberAuth memberAuth)  throws ApiException {
        Long couponMappingSeq = couponPromotionService.save(channelType, couponCode,memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, couponMappingSeq);
    }

    @ApiOperation(value = "쿠폰 조회 API", notes = "쿠폰 조회 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/promotion/{channelType}")
    public ApiResponseDto<List<PaymentCouponRes>> findCouponSearch(@PathVariable String channelType, MemberAuth memberAuth) {
        List<PaymentCouponRes> result = couponPromotionService.findCouponSearch(channelType, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, result);
    }

}

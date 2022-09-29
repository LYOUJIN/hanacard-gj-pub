package com.exflyer.oddi.user.api.my;


import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.coupon.service.CouponPromotionService;
import com.exflyer.oddi.user.api.my.dto.MyCouponReq;
import com.exflyer.oddi.user.api.my.dto.MyPromotionResult;
import com.exflyer.oddi.user.api.my.service.MyCouponMngService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "사용자 쿠폰 인베토리 기능", protocols = "http")
@Slf4j
@RestController
public class MyCouponMngApi {

    @Autowired
    private MyCouponMngService myCouponMngService;

    @Autowired
    private CouponPromotionService couponPromotionService;

    @ApiOperation(value = "사용자 쿠폰 조회 API", notes = "사용자 쿠폰 조회 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/my/coupon")
    public ApiResponseDto<Map> findList(MyCouponReq myCouponReq, MemberAuth memberAuth) {
        myCouponReq.setMemberId(memberAuth.getId());
        Map res = myCouponMngService.findList(myCouponReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
    }

    @ApiOperation(value = "사용자 쿠폰 등록 API", notes = "사용자 쿠폰 등록 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/my/coupon/{couponCode}")
    public ApiResponseDto save(@PathVariable String couponCode, MemberAuth memberAuth) throws ApiException {
        couponPromotionService.save("",couponCode, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, null);
    }

}

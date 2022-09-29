package com.exflyer.oddi.user.api.coupon.dao;

import com.exflyer.oddi.user.api.my.dto.MyCouponReq;
import com.exflyer.oddi.user.api.my.dto.MyPromotionResult;
import com.exflyer.oddi.user.models.CouponMapping;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponPromotionMapper {

    Page<MyPromotionResult> findList(MyCouponReq myCouponReq);
    int findListUsableCnt(MyCouponReq myCouponReq);

    int findByIslreadyCoupon(String couponCode, String today, String memberId);
}

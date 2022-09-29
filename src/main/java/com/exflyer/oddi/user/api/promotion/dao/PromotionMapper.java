package com.exflyer.oddi.user.api.promotion.dao;

import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponReq;
import com.exflyer.oddi.user.api.promotion.dto.PromotionCouponResult;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionMapper {

    PromotionCouponResult findPromotionCoupon(PromotionCouponReq req);

}

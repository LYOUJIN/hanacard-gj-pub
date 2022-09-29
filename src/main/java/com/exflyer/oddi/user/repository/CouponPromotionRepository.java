package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.CouponPromotion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponPromotionRepository extends JpaRepository<CouponPromotion, Long>,
    JpaSpecificationExecutor<CouponPromotion> {

    @Query(value="select * from coupon_promotion where seq = :promotion_seq and (promotion_channel_type = 'PCT001' or promotion_channel_type in (:promotionChannelType))", nativeQuery = true)
    CouponPromotion findByUsableCount(@Param("promotion_seq") Long promotionSeq,@Param("promotionChannelType") String... promotionChannelType);

    @Query(value = "select * from coupon_promotion "
        + "where type = :type and discount_type = :discount_type and usable=true", nativeQuery = true)
    CouponPromotion findAllBySeq(String type, String discount_type);
}

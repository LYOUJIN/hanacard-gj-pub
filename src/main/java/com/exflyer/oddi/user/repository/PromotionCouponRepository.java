package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PromotionCoupon;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PromotionCouponRepository extends JpaRepository<PromotionCoupon, Long>,JpaSpecificationExecutor<PromotionCoupon> {

    @Modifying
    @Transactional
    @Query(value = "update promotion_coupon set expired_date = :expiredDate where promotion_seq = :seq", nativeQuery = true)
    void saveCouponExpiredDate(@Param("expiredDate") String expiredDate, @Param("seq") Long seq);

    @Query(value="select count(1) from promotion_coupon where coupon_code = :couponCode and usable = :usable and del = false", nativeQuery = true)
    int findByIsValidCoupon(@Param("couponCode") String couponCode, @Param("usable") Boolean usable);

    @Query(value="select count(1) from promotion_coupon where member_id = :memberId and coupon_code = :couponCode and usable = :usable and del = false", nativeQuery = true)
    int findByIsMemberValidCoupon(@Param("memberId") String memberId, @Param("couponCode") String couponCode, @Param("usable") Boolean usable);

    @Modifying
    @Transactional
    @Query(value = "update promotion_coupon set usable = :usable, using_date = :usingDate where member_id = :memberId and coupon_code = :couponCode and del = false", nativeQuery = true)
    void saveByMemberCouponUsing(@Param("usable") boolean usable, @Param("usingDate") LocalDateTime usingDate,
        @Param("memberId") String memberId, @Param("couponCode") String couponCode);

    @Modifying
    @Transactional
    @Query(value = "update promotion_coupon set member_id = :memberId where coupon_code = :couponCode and coupon_code = :couponCode and usable = false and del = false", nativeQuery = true)
    void saveByMemberCoupon(@Param("memberId") String memberId, @Param("couponCode") String couponCode);

    @Modifying
    @Transactional
    @Query(value = "update promotion_coupon set usable = false, using_date = null where member_id = :memberId and coupon_code = :couponCode and del = false", nativeQuery = true)
    void updateCouponCancel(@Param("memberId") String memberId, @Param("couponCode") String couponCode);


}

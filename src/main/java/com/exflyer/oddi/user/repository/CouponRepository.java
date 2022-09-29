package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long>,
    JpaSpecificationExecutor<Coupon> {

    @Query(value="select * "
        + "from coupon c inner join coupon_promotion cp "
        + "on c.promotion_seq = cp.seq "
        + "and cp.usable = true "
        + "where c.coupon_code = :couponCode "
        + "and c.del = false"
        + "", nativeQuery = true)
    Coupon findByIsValidCoupon(@Param("couponCode") String couponCode);

}
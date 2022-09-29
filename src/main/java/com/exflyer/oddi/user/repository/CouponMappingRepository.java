package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.CouponMapping;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CouponMappingRepository extends JpaRepository<CouponMapping, Long>,
    JpaSpecificationExecutor<CouponMapping> {


    @Query(value="select count(1) from coupon_mapping "
        + "where seq = :couponMappingSeq "
        + "and member_id = :memberId and usable = false ", nativeQuery = true)
    int findByCouponInfo(@Param("memberId") String memberId, @Param("couponMappingSeq") Long couponMappingSeq);

    @Modifying
    @Transactional
    @Query(value = "update coupon_mapping set usable = :usable, using_date = :usingDate, payment_seq = :paymentSeq "
        + "where member_id = :memberId and seq = :couponMappingSeq", nativeQuery = true)
    void saveByMemberCouponUsing(@Param("usable") boolean usable, @Param("usingDate") LocalDateTime usingDate,
        @Param("memberId") String memberId, @Param("couponMappingSeq") Long couponMappingSeq,@Param("paymentSeq") Long paymentSeq);

    @Modifying
    @Transactional
    @Query(value = "update coupon_mapping set usable = false, using_date = null, payment_seq = null "
        + "where member_id = :memberId and seq = :couponMappingSeq", nativeQuery = true)
    void updateCouponCancel(@Param("memberId") String memberId, @Param("couponMappingSeq") Long couponMappingSeq);

}
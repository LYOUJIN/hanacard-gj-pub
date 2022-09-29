package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Adv;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdvRepository  extends JpaRepository<Adv, Long>, JpaSpecificationExecutor<Adv> {


    @Modifying
    @Transactional
    @Query(value = "update adv set progress_code = :progressCode, mod_date = :modDate "
        + "where seq = :advSeq and member_id = :memberId", nativeQuery = true)
    void updateCancelProgress(@Param("progressCode") String progressCode, @Param("advSeq") Long advSeq
        , @Param("modDate") LocalDateTime modDate,@Param("memberId") String memberId);

    @Modifying
    @Transactional
    @Query(value = "update adv set user_check = :userCheck where seq = :advSeq and member_id = :memberId", nativeQuery = true)
    void updateByUserCheck(@Param("advSeq") Long advSeq,@Param("memberId") String memberId, @Param("userCheck") Boolean userCheck);

    @Modifying
    @Transactional
    @Query(value = "update adv set coupon_number = null where seq = :advSeq and member_id = :memberId", nativeQuery = true)
    void updateCouponCode(@Param("advSeq") Long advSeq,@Param("memberId") String memberId);

}
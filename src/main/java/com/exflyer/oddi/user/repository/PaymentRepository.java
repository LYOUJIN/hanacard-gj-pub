package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Payment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Long>,
    JpaSpecificationExecutor<Payment> {

    @Query(value = "select * from payment where seq  = :paymentSeq", nativeQuery = true)
    Payment findPaymentInfo(@Param("paymentSeq") Long paymentSeq);

    @Modifying
    @Transactional
    @Query(value = "update payment set type = :type, pay_cancel_date = :payCancelDate, pay_cancel_type = :payType "
        + "where seq = :paymentSeq and member_id = :memberId", nativeQuery = true)
    void saveByIdType(@Param("type") String type, @Param("paymentSeq") Long paymentSeq,@Param("memberId") String memberId
        , @Param("payCancelDate") LocalDateTime payCancelDate,@Param("payType") Boolean payType);

    @Query(value = "select * from payment where seq  = :paymentSeq and  member_id = :id", nativeQuery = true)
    Payment findPaymentList(@Param("paymentSeq") Long paymentSeq, @Param("id") String id);

    List<Payment> findByMemberIdAndType(@Param("memberId") String memberId,@Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "update payment set pay_type = :isMobile where seq = :paymentSeq", nativeQuery = true)
    void updatePayType(@Param("paymentSeq") Long paymentSeq,@Param("isMobile") Boolean isMobile);


}


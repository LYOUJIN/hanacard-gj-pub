package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Terms;
import com.exflyer.oddi.user.models.Voc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InquireRepository extends JpaRepository<Voc, Long>, JpaSpecificationExecutor<Voc> {

    Optional<Voc> findBySeqAndMemberId(long seq, String memberId);


    @Modifying
    @Transactional
    @Query(value = "update voc set user_check = :userCheck where seq = :seq and member_id = :memberId", nativeQuery = true)
    void updateByUserCheck(@Param("seq") Long advSeq,@Param("memberId") String memberId, @Param("userCheck") Boolean userCheck);

    @Query(value = "select count(1) from voc where member_id = :memberId and user_check = false", nativeQuery = true)
    int findByMemberId(@Param("memberId") String memberId);

}

package com.exflyer.oddi.user.repository.jpa;

import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.PartnerConfig;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {

    @Query(value = "select count(*) from member where email = :email and provider = :provider", nativeQuery = true)
    int isDuplicationId(String email, String provider);

    @Query(value = "select * from member where email = :email and phone_number = :phoneNumber", nativeQuery = true)
    Member findByIdEmail(String email, String phoneNumber);

    @Query(value = "select * from member where id = :id and password = :password", nativeQuery = true)
    Member findByIdPassword(String id, String password);

    @Query(value = "select * from member where id = :id", nativeQuery = true)
    Member findByPassword(String id);

    @Modifying
    @Transactional
    @Query(value = "update member set password = :password, password_reset = true,  password_mod_date = :regDate where id = :id", nativeQuery = true)
    void updateMemberPassword(@Param("id") String id, @Param("password") String password, @Param("regDate") LocalDateTime regDate);

    @Query(value = "select * from member where mustad_id = :mustadId", nativeQuery = true)
    Member findMustadId(@Param("mustadId") String mustadId);

    @Modifying
    @Transactional
    @Query(value = "update member set mustad_mapping_done = true, mustad_mapping_reg_date = :regDate where mustad_id = :mustadId", nativeQuery = true)
    void updateMustadMappingDone(@Param("mustadId") String mustadId,@Param("regDate") LocalDateTime regDate);

}

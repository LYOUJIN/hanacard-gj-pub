package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.MemberCompany;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MemberCompanyRepository extends JpaRepository<MemberCompany, Long>,
  JpaSpecificationExecutor<MemberCompany> {

  @Query(value = "select * from member_company where member_id = :memberId", nativeQuery = true)
  List<MemberCompany> findAllByMemberId(String memberId);
}

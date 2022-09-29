package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.WithdrawalMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WithdrawalMemberRepository extends JpaRepository<WithdrawalMember, Long>,JpaSpecificationExecutor<WithdrawalMember> {
}

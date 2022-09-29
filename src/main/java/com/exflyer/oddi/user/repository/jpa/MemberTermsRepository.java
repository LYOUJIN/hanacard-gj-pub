package com.exflyer.oddi.user.repository.jpa;

import com.exflyer.oddi.user.models.MemberTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberTermsRepository extends JpaRepository<MemberTerms, Long>, JpaSpecificationExecutor<MemberTerms> {

}

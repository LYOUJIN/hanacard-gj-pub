package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TermsRepository extends JpaRepository<Terms, Long>, JpaSpecificationExecutor<Terms> {

}

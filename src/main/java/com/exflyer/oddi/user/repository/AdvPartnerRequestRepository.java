package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvPartnerRequestRepository extends JpaRepository<PartnerRequest, Long>, JpaSpecificationExecutor<PartnerRequest> {

}

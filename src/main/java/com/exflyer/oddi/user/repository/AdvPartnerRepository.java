package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.AdvPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvPartnerRepository extends JpaRepository<AdvPartner, Long>, JpaSpecificationExecutor<AdvPartner> {

}

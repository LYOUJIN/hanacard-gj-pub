package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PartnerConfig;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PatnerConfigRepository extends JpaRepository<PartnerConfig, Integer>, JpaSpecificationExecutor<PartnerConfig> {

}


package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PgAccreditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PgAccreditLogRepository extends JpaRepository<PgAccreditLog, Long>,
    JpaSpecificationExecutor<PgAccreditLog> {


}

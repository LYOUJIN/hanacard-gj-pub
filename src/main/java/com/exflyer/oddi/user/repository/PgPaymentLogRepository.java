package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PgPaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PgPaymentLogRepository extends JpaRepository<PgPaymentLog, Long>,
    JpaSpecificationExecutor<PgPaymentLog> {
}

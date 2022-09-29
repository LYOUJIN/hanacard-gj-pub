package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.NotificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationGroupRepository extends JpaRepository<NotificationGroup, Long>,
    JpaSpecificationExecutor<NotificationGroup> {

}
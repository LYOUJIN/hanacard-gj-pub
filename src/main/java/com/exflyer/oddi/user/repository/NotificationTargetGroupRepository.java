package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.NotificationTargetGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationTargetGroupRepository extends JpaRepository<NotificationTargetGroup, String>,
  JpaSpecificationExecutor<NotificationTargetGroup> {

}

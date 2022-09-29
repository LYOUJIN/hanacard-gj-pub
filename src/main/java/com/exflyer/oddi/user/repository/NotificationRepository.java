package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
    JpaSpecificationExecutor<Notification> {

}
package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface YouTubeRepository extends JpaRepository<Youtube, String>, JpaSpecificationExecutor<Youtube> {
}

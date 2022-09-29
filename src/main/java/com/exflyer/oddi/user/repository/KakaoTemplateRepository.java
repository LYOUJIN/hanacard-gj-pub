package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.KakaoTemplate;
import com.exflyer.oddi.user.models.LiveSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KakaoTemplateRepository extends JpaRepository<KakaoTemplate, String>, JpaSpecificationExecutor<KakaoTemplate> {
}

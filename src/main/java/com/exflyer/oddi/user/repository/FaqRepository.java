package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.AdvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FaqRepository extends JpaRepository<AdvFile, Long>, JpaSpecificationExecutor<AdvFile> {
    @Transactional
    @Modifying
    @Query(value = "update faq set click_count = click_count+1 where seq = :seq", nativeQuery = true)
    void updateClickCount(@Param("seq")Long seq);

}
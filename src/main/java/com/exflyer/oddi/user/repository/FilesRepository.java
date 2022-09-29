package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Files;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FilesRepository extends JpaRepository<Files, Long>, JpaSpecificationExecutor<Files> {

    @Transactional
    @Modifying
    @Query(value = "update files set mapping_done = true where seq in (:fileSeqList)", nativeQuery = true)
    void updateUseAble(List<Long> fileSeqList);

}

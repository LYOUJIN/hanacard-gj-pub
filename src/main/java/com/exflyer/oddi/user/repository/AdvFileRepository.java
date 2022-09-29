package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.AdvFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvFileRepository  extends JpaRepository<AdvFile, Long>, JpaSpecificationExecutor<AdvFile> {

    List<AdvFile> findByAdvSeq(@Param("advSeq") Long advSeq);

    @Query(value = "select file_seq from adv_file where adv_seq  = :advSeq and file_seq not in (:fileSeq)", nativeQuery = true)
    List<Long> isNotInFileSeq(@Param("advSeq") Long advSeq, @Param("fileSeq") List<Long> fileSeq);
}
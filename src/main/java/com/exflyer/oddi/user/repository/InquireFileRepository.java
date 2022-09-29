package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PartnerConfig;
import com.exflyer.oddi.user.models.Terms;
import com.exflyer.oddi.user.models.Voc;
import com.exflyer.oddi.user.models.VocFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InquireFileRepository extends JpaRepository<VocFile, Long>, JpaSpecificationExecutor<VocFile> {
    @Query(value = "select file_seq from voc_file where voc_seq = :vocSeq and file_seq != :fileSeq", nativeQuery = true)
    List<Long> findFileSeqBySeqNotFileSeq(long vocSeq, long fileSeq);

    void deleteAllByVocFilePk_VocSeq(long vocSeq);
}

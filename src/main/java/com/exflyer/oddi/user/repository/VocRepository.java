package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Voc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VocRepository extends JpaRepository<Voc, Long>, JpaSpecificationExecutor<Voc> {

}

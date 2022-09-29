package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.CodeGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, String>, JpaSpecificationExecutor<CodeGroup> {

  List<CodeGroup> findAllByUsable(boolean usable);
}

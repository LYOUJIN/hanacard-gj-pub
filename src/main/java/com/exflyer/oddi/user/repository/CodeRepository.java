package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Code;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, String> {

    List<Code> findByGroupCodeAndUsableOrderByOrderingAsc(String groupCode, boolean usable);
    List<Code> findByGroupCode(String groupCode);

}

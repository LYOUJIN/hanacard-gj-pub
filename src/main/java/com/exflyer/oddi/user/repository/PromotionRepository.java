package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.Promotion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PromotionRepository extends JpaRepository<Promotion, Long>,
    JpaSpecificationExecutor<Promotion> {

    @Query(value = "select * from promotion where type = :type and discount_type = :discount_type and usable=true", nativeQuery = true)
    Promotion findAllBySeq(String type, String discount_type);

}

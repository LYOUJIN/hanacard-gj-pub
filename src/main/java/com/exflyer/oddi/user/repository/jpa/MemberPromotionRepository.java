package com.exflyer.oddi.user.repository.jpa;

import com.exflyer.oddi.user.models.MemberPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberPromotionRepository extends JpaRepository<MemberPromotion, Long>,
  JpaSpecificationExecutor<MemberPromotion> {

}

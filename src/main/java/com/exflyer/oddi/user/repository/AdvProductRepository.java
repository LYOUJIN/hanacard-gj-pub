package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.AdvProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdvProductRepository extends JpaRepository<AdvProduct, Long>, JpaSpecificationExecutor<AdvProduct> {

}
package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.PhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, String>, JpaSpecificationExecutor<PhoneAuth> {


  boolean existsByPhoneNumberAndConfirm(String encryptPhoneNumber, boolean confirm);
}

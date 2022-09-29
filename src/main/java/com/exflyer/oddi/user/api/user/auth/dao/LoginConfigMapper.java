package com.exflyer.oddi.user.api.user.auth.dao;

import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.share.dto.OddiLoginConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginConfigMapper {

  OddiLoginConfig find();
  Member findAllByMember(String email, String provider);
}

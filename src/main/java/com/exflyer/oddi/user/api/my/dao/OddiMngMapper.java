package com.exflyer.oddi.user.api.my.dao;

import com.exflyer.oddi.user.api.my.dto.OddiMng;
import com.exflyer.oddi.user.api.my.dto.OddiMngPartner;
import com.exflyer.oddi.user.api.my.dto.OddiMngResult;
import com.github.pagehelper.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OddiMngMapper {

    Page<OddiMngResult> findList(OddiMng oddiMng);
    List<OddiMngPartner> findPartnerList(OddiMng oddiMng);
    List<OddiMngPartner> findProductPartnerList(OddiMng oddiMng);

    int findByMemberId(String memberId);

}

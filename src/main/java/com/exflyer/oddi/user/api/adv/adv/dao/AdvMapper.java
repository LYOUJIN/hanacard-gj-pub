package com.exflyer.oddi.user.api.adv.adv.dao;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvProductPartnerRes;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvSearchReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvPartnerRes;
import com.exflyer.oddi.user.api.adv.adv.dto.MemberCompanyRes;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerConfigReq;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerFiles;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvMapper {

    List<AdvPartnerRes> findAdvPartnerCode(AdvSearchReq seqs);
    List<AdvProductPartnerRes> findAdvPartnerProductCode(AdvSearchReq seqs);
    MemberCompanyRes findMemberCompany(AdvSearchReq req);

    List<PartnerConfigReq> findPartnerConfig(String channelType);
    List<PartnerFiles> findDefaultAdvFiles(String channelType);



}

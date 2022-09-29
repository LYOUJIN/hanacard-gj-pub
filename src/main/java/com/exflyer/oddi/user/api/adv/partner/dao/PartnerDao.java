package com.exflyer.oddi.user.api.adv.partner.dao;


import com.exflyer.oddi.user.api.adv.partner.dto.PartnerInfo;
import com.exflyer.oddi.user.api.adv.subway.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PartnerDao {

    List<String> findGroupByAddrGu();
    List<PartnerInfo> findPartnerMall(String guName, String channelType);

}

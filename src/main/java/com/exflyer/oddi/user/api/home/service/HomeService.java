package com.exflyer.oddi.user.api.home.service;

import com.exflyer.oddi.user.api.adv.subway.dao.SubwayMapper;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayInfoList;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerListRes;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerSearchReq;
import com.exflyer.oddi.user.api.home.dao.HomeMapper;
import com.exflyer.oddi.user.api.home.dto.HomeMainRes;
import com.exflyer.oddi.user.share.LocalDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HomeService {

    @Autowired
    private HomeMapper homeMapper;

    public HomeMainRes getMainData() {
        HomeMainRes res = new HomeMainRes();
        String targetDate = LocalDateUtils.krNowByFormatter("yyyyMMdd");
        res.setSliders(homeMapper.findByMainSlider(targetDate));
        res.setPartners(homeMapper.findByMainPartners());
        res.setPartnerCnt(homeMapper.findByMainPartnersCnt());
        return res;
    }

}

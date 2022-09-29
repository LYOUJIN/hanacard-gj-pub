package com.exflyer.oddi.user.api.banner.service;

import com.exflyer.oddi.user.api.banner.dao.BannerMapper;
import com.exflyer.oddi.user.api.banner.dto.BannerResult;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.LocalDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    public List<BannerResult> findBanner() throws ApiException {
        return bannerMapper.findBanner(LocalDateUtils.krNowByFormatter("yyyyMMdd"));
    }
}

package com.exflyer.oddi.user.api.banner.dao;

import com.exflyer.oddi.user.api.banner.dto.BannerResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper {

    List<BannerResult> findBanner(String today);
}

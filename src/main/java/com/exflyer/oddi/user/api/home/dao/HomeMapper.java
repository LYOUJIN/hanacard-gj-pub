package com.exflyer.oddi.user.api.home.dao;



import com.exflyer.oddi.user.api.home.dto.HomeMainPartners;
import com.exflyer.oddi.user.api.home.dto.HomeMainSlider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeMapper {
    List<HomeMainSlider> findByMainSlider(String targetDate);
    List<HomeMainPartners> findByMainPartners();
    int findByMainPartnersCnt();
}

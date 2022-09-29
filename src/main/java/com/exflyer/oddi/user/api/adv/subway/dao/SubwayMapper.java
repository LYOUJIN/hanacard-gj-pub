package com.exflyer.oddi.user.api.adv.subway.dao;


import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotRes;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerName;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayImage;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayLine;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayName;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerListRes;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerSearchReq;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface SubwayMapper {

    List<SubwayLine> findAbleSubwayLineList(long seq,long productSeq, String channelType);

    List<SubwayName> findPartnerSubwayName(String channelType);

    List<SubwayPartnerListRes> findSubwayPartnerList(SubwayPartnerSearchReq req);

    List<SubwayImage> findSubwayPartnerImage(long seq);

    List<SubwayImage> findSubwayProductPartnerImage(long productSeq);

    List<AdvReadyPartnerSlotRes> findReadyPartnerSlotList(AdvReadyPartnerSlotReq req);

    List<SubwayLine> findAbleSubwayList(long seq,long productSeq, String channelType, boolean isProduct);

    List<PartnerName> findSubwayNameList(String channelType);


}

package com.exflyer.oddi.user.api.live.service;

import com.exflyer.oddi.user.api.live.dao.OddiLiveMapper;
import com.exflyer.oddi.user.api.live.dto.LiveStreamDetailResult;
import com.exflyer.oddi.user.api.live.dto.SearchVodReq;
import com.exflyer.oddi.user.api.live.dto.YouTubeVodResult;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeConditionRes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.LiveSchedule;
import com.exflyer.oddi.user.models.LiveStreaming;
import com.exflyer.oddi.user.models.Youtube;
import com.exflyer.oddi.user.repository.LiveScheduleRepository;
import com.exflyer.oddi.user.repository.LiveStreamingRepository;
import com.exflyer.oddi.user.repository.YouTubeRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.PagingResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OddiLiveService {

    @Autowired
    private YouTubeRepository youTubeRepository;
    @Autowired
    private LiveStreamingRepository liveStreamingRepository;
    @Autowired
    private LiveScheduleRepository liveScheduleRepository;
    @Autowired
    private OddiLiveMapper oddiLiveMapper;

    public PagingResult findVodList(SearchVodReq searchVodReq){

        PageHelper.startPage(searchVodReq.getPageNo(), searchVodReq.getPageSize());
        Page<YouTubeVodResult> result = oddiLiveMapper.findByYoutubeList(searchVodReq);
        return PagingResult.createResultDto(result);
    }

    public YouTubeVodResult findIsNewVodInfo(String nowDate){
        return oddiLiveMapper.findIsNewVodInfo(nowDate);
    }

    public LiveStreamDetailResult findLiveStreamingInfo(){
        String nowDate = LocalDateUtils.krNowByFormatter("yyyyMMdd");
        String nowTime = LocalDateUtils.krNowByFormatter("HHmm");

        LiveStreaming liveStreaming = liveStreamingRepository.findByChannelInfo();
        LiveSchedule liveSchedules = liveScheduleRepository.findByScheduleInfo(nowDate, nowTime);

        return new LiveStreamDetailResult(liveStreaming, liveSchedules);
    }

    public List<LiveSchedule> findLiveScheduleList(){
        String nowDate = LocalDateUtils.krNowByFormatter("yyyyMMdd");
        return liveScheduleRepository.findByScheduleList(nowDate);
    }

    public Youtube findVodInfo(String youtubeId) throws ApiException {
        Youtube youtube =  oddiLiveMapper.findByVodYoutubeList(youtubeId);
        return youtube;
    }

}

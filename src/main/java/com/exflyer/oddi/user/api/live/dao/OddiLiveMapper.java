package com.exflyer.oddi.user.api.live.dao;

import com.exflyer.oddi.user.api.live.dto.SearchVodReq;
import com.exflyer.oddi.user.api.live.dto.YouTubeVodResult;
import com.exflyer.oddi.user.models.Youtube;
import com.github.pagehelper.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OddiLiveMapper {
    Page<YouTubeVodResult> findByYoutubeList(SearchVodReq searchVodReq);
    YouTubeVodResult findIsNewVodInfo(String nowDate);
    Youtube findByVodYoutubeList(String youtubeId);
}

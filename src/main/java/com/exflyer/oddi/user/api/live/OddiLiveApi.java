package com.exflyer.oddi.user.api.live;

import com.exflyer.oddi.user.api.live.dto.LiveStreamDetailResult;
import com.exflyer.oddi.user.api.live.dto.SearchVodReq;
import com.exflyer.oddi.user.api.live.dto.YouTubeVodResult;
import com.exflyer.oddi.user.api.live.service.OddiLiveService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.LiveSchedule;
import com.exflyer.oddi.user.models.Youtube;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "오디라이브", protocols = "http")
@Slf4j
@RestController
public class OddiLiveApi {

    @Autowired
    private OddiLiveService oddiLiveService;

    @ApiOperation(value = "vod목록 조회", notes = "vod목록 조회 API 입니다. ")
    @GetMapping(path = "/oddi-live/vod-list")
    public ApiResponseDto<List<YouTubeVodResult>> findVodList(SearchVodReq searchVodReq) {
        PagingResult searchVodList = oddiLiveService.findVodList(searchVodReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, searchVodList);
    }

    @ApiOperation(value = "최신 vod 조회(조회날짜-시간기준)", notes = "최신 vod 조회(조회날짜-시간기준) API 입니다. ")
    @GetMapping(path = "/oddi-live/is-new-vod")
    public ApiResponseDto<YouTubeVodResult> findIsNewVodInfo() {
        YouTubeVodResult searchVodInfo = oddiLiveService.findIsNewVodInfo(LocalDateUtils.krNowByFormatter("yyyyMMddHHmmss"));
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, searchVodInfo);
    }

    @ApiOperation(value = "실시간 스트리밍 정보 조회(현재시간 기준 스케쥴로 등록된것)", notes = "실시간 스트리밍 정보 조회(현재시간 기준 스케쥴로 등록된것) API 입니다. ")
    @GetMapping(path = "/oddi-live/live-streaming")
    public ApiResponseDto<LiveStreamDetailResult> findLiveStreamingInfo() {
        LiveStreamDetailResult searchVodInfo = oddiLiveService.findLiveStreamingInfo();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, searchVodInfo);
    }

    @ApiOperation(value = "등록된 스트리밍 정보 조회(금일기준 스케쥴 조회)", notes = "등록된 스트리밍 정보 조회(금일기준 스케쥴 조회) API 입니다. ")
    @GetMapping(path = "/oddi-live/live-schedule")
    public ApiResponseDto<List<LiveSchedule>> findLiveScheduleList() {
        List<LiveSchedule> searchScheduleList = oddiLiveService.findLiveScheduleList();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, searchScheduleList);
    }

    @ApiOperation(value = "vod 상세 조회", notes = "vod 상세 조회 API 입니다. ")
    @GetMapping(path = "/oddi-live/vod/{youtubeId}")
    public ApiResponseDto<Youtube> findVodInfo(@PathVariable String youtubeId) throws ApiException {
        Youtube searchVodInfo = oddiLiveService.findVodInfo(youtubeId);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, searchVodInfo);
    }

}

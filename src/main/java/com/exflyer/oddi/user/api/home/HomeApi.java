package com.exflyer.oddi.user.api.home;

import com.exflyer.oddi.user.api.home.dto.HomeMainRes;
import com.exflyer.oddi.user.api.home.service.HomeService;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqCondition;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqConditionResult;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@Api(tags = "홈페이지 메인", protocols = "http")
@Slf4j
@RestController
public class HomeApi {

    @Autowired
    private HomeService homeService;

    @ApiOperation(value = "메인화면 슬라이드, 파트너 조회 ", notes = "메인화면 슬라이드, 파트너 조회")
    @GetMapping(path = "/home")
    public ApiResponseDto<HomeMainRes> findMain() {
        HomeMainRes res = homeService.getMainData();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
    }

}

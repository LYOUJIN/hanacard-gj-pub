package com.exflyer.oddi.user.api.banner;

import com.exflyer.oddi.user.api.banner.dto.BannerResult;
import com.exflyer.oddi.user.api.banner.service.BannerService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "배너", protocols = "http")
@Slf4j
@RestController
public class BannerApi {

    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "메인 팝업 API", notes = "메인 팝업 API 입니다. ")
    @GetMapping(path = "/pop/banner")
    public ApiResponseDto<List<BannerResult>> findList() throws ApiException {
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, bannerService.findBanner());
    }
}

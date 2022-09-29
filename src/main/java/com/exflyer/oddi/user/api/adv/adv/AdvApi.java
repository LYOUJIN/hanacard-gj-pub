package com.exflyer.oddi.user.api.adv.adv;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvProductSearchResult;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvSearchReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvSearchResult;
import com.exflyer.oddi.user.api.adv.adv.service.AdvService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.ChannelCodes;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "광고신청 공통코드", protocols = "http")
@Slf4j
@RestController
public class AdvApi {

    @Autowired
    private AdvService advService;

    @ApiOperation(value = "광고신청 공통코드 조회 API", notes = "광고신청 공통코드 조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/adv/partner/comm")
    public ApiResponseDto<AdvSearchResult> findAdvPartnerComm(@Validated @RequestBody AdvSearchReq req, MemberAuth memberAuth) {
        req.setChannelType(ChannelCodes.ODDI_ZONE.getCode());
        req.setRegId(memberAuth.getId());
        AdvSearchResult advSearchResult = advService.findAdvPartnerComm(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, advSearchResult);
    }

    @ApiOperation(value = "광고신청 묶음상품 공통코드 조회 API", notes = "광고신청 묶음상품 공통코드 조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/adv/product/comm")
    public ApiResponseDto<AdvSearchResult> findAdvPartnerProductComm(@Validated @RequestBody AdvSearchReq req, MemberAuth memberAuth) {
        req.setChannelType(ChannelCodes.ODDI_ZONE.getCode());
        req.setRegId(memberAuth.getId());
        AdvProductSearchResult advSearchResult = advService.findAdvPartnerProductComm(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, advSearchResult);
    }

    @ApiOperation(value = "광고신청 지하철공통코드 조회 API", notes = "광고신청 공통코드 조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/adv/subway/comm")
    public ApiResponseDto<AdvSearchResult> findAdvSubwayCode(@Validated @RequestBody AdvSearchReq req, MemberAuth memberAuth) {
        req.setChannelType(ChannelCodes.SUBWAY.getCode());
        req.setRegId(memberAuth.getId());
        AdvSearchResult advSearchResult = advService.findAdvPartnerComm(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, advSearchResult);
    }

    @ApiOperation(value = "광고신청 지하철 묶음상품 공통코드 조회 API", notes = "광고신청 지하철 묶음상품 공통코드 조회 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/adv/subway/product/comm")
    public ApiResponseDto<AdvSearchResult> findAdvSubwayPartnerProductComm(@Validated @RequestBody AdvSearchReq req, MemberAuth memberAuth) {
        req.setChannelType(ChannelCodes.SUBWAY.getCode());
        req.setRegId(memberAuth.getId());
        AdvProductSearchResult advSearchResult = advService.findAdvPartnerProductComm(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, advSearchResult);
    }
}

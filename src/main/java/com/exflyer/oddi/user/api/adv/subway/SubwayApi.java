package com.exflyer.oddi.user.api.adv.subway;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerName;
import com.exflyer.oddi.user.api.adv.adv.service.AdvService;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiReadyPartnerSlotSearchResult;
import com.exflyer.oddi.user.api.adv.oddi.service.OddiService;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayInfoList;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayName;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerListRes;
import com.exflyer.oddi.user.api.adv.subway.dto.SubwayPartnerSearchReq;
import com.exflyer.oddi.user.api.adv.subway.service.SubwayService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.AdvAuditCodes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.ChannelCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "지하철", protocols = "http")
@Slf4j
@RestController
public class SubwayApi {

    @Autowired
    private AdvService advService;

    @Autowired
    private SubwayService subwayService;

    @ApiOperation(value = "지하철 등록 API", notes = "오디존 등록 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/subway")
    public ApiResponseDto save(@Validated @RequestBody AdvAddReq advAddReq, MemberAuth memberAuth) throws ApiException, ParseException,Exception {
        advAddReq.setRegId(memberAuth.getId());
        advAddReq.setRegDate(LocalDateUtils.krNow());
        advAddReq.setChannelType(ChannelCodes.SUBWAY.getCode());
        advAddReq.setProgressCode(AdvAuditCodes.PROGRESS_RECEIPT.getCode());
        Long advSeq = advService.save(advAddReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS,advSeq);
    }

    @ApiOperation(value = "지하철 리스트 API", notes = "지하철 리스트 API 입니다. ")
    @GetMapping(path = "/subway/list")
    public ApiResponseDto<SubwayInfoList> get() {
        SubwayInfoList subwayInfoList = subwayService.getSubwayList();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, subwayInfoList);
    }

    @ApiOperation(value = "지하철 파트너 리스트 API", notes = "지하철 파트너 리스트  API 입니다. ")
    @GetMapping(path = "/subway/partners")
    public ApiResponseDto<SubwayPartnerListRes> getPartner(@Validated SubwayPartnerSearchReq req) {
        req.setChannelType(ChannelCodes.SUBWAY.getCode());
        List<SubwayPartnerListRes> subwayInfoList = subwayService.getPartnerList(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, subwayInfoList);
    }

    @ApiOperation(value = "지하철 사용가능 슬롯 조회 API", notes = "지하철 사용가능 슬롯 조회 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/subway/adv/partner/slot")
    public ApiResponseDto<OddiReadyPartnerSlotSearchResult> findReadyPartnerSlot (
        AdvReadyPartnerSlotReq req) throws ParseException {
        OddiReadyPartnerSlotSearchResult subwayReadyPartnerSlotSearchResult =
            OddiReadyPartnerSlotSearchResult.builder().partnerSlotList(subwayService.findReadyPartnerSlotList(req)).build();

        return new ApiResponseDto(ApiResponseCodes.SUCCESS, subwayReadyPartnerSlotSearchResult);
    }

    @ApiOperation(value = "지하철명 리스트 API", notes = "지하철명 리스트 API 입니다. ")
    @GetMapping(path = "/subway/name-list")
    public ApiResponseDto<PartnerName> findSubwayNameList() {
        List<PartnerName> subwayNameList = subwayService.findSubwayNameList();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, subwayNameList);
    }

}

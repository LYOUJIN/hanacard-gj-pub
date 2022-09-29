package com.exflyer.oddi.user.api.adv.oddi;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvReadyPartnerSlotReq;
import com.exflyer.oddi.user.api.adv.adv.dto.PartnerName;
import com.exflyer.oddi.user.api.adv.adv.service.AdvService;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiHistoryResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiProductiDetailResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiReadyPartnerSlotSearchResult;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiReq;
import com.exflyer.oddi.user.api.adv.oddi.dto.OddiResult;
import com.exflyer.oddi.user.api.adv.oddi.service.OddiService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.AdvAuditCodes;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.ChannelCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Youtube;
import com.exflyer.oddi.user.share.LocalDateUtils;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "오디존", protocols = "http")
@Slf4j
@RestController
public class OddiApi {

    @Autowired
    private AdvService advService;

    @Autowired
    private OddiService oddiService;

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ApiOperation(value = "오디존 리스트 조회 API", notes = "오디존 리스트 조회 API 입니다. ")
    @GetMapping(path = "/oddi")
    public ApiResponseDto<List<OddiResult>> findList(OddiReq oddiReq) {
        oddiReq.setOperation(true);
        oddiReq.setChannelType(ChannelCodes.ODDI_ZONE.getCode());
        List<OddiResult> oddiRes = oddiService.findList(oddiReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiRes);
    }

    @ApiOperation(value = "오디존 묶음상품 조회 API", notes = "오디존 묶음상품 조회 API 입니다. ")
    @GetMapping(path = "/oddi/product")
    public ApiResponseDto<List<OddiProductResult>> findProductList(OddiReq oddiReq) {
        oddiReq.setOperation(true);
        oddiReq.setChannelType(ChannelCodes.ODDI_ZONE.getCode());
        List<OddiProductResult> oddiResult = oddiService.findProductList(oddiReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiResult);
    }

    @ApiOperation(value = "오디존 등록 API", notes = "오디존 등록 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/oddi")
    public ApiResponseDto save(@Validated @RequestBody AdvAddReq advAddReq, MemberAuth memberAuth) throws ApiException, ParseException {
        advAddReq.setRegId(memberAuth.getId());
        advAddReq.setRegDate(LocalDateUtils.krNow());
        advAddReq.setChannelType(ChannelCodes.ODDI_ZONE.getCode());
        advAddReq.setProgressCode(AdvAuditCodes.PROGRESS_RECEIPT.getCode());
        Long advSeq = advService.save(advAddReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS,advSeq);
    }

    @ApiOperation(value = "오디존/지하철 상세보기 API", notes = "오디존/지하철 상세보기 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/oddi/{advSeq}")
    public ApiResponseDto<OddiDetailResult> detail(@PathVariable Long advSeq, MemberAuth memberAuth) {
        OddiDetailResult oddiDetailResult = oddiService.findOddiDetailResult(advSeq, memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiDetailResult);
    }

    @ApiOperation(value = "오디존 묶음상품 상세보기 API", notes = "오디존 묶음상품 상세보기 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/oddi/product/{advSeq}")
    public ApiResponseDto productDetail(@PathVariable Long advSeq, MemberAuth memberAuth) {
        OddiProductiDetailResult oddiProductiDetailResult = oddiService.findAdvProductDetail(advSeq, memberAuth.getId());;
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiProductiDetailResult);
    }

    @ApiOperation(value = "오디존 수정", notes = "오디존 수정 API 입니다. ")
    @LoginNeedApi
    @PutMapping(path = "/oddi/{seq}")
    public ApiResponseDto modify(@PathVariable Long seq, @RequestBody AdvAddReq advAddReq, MemberAuth memberAuth) throws ApiException {
        advAddReq.setRegId(memberAuth.getId());
        advAddReq.setAdvSeq(seq);
        advAddReq.setRegDate(LocalDateUtils.krNow());
        advService.modify(advAddReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }

    @ApiOperation(value = "오디존 이전광고 조회 API", notes = "오디존 이전광고 조회 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/oddi/history")
    public ApiResponseDto<List<OddiHistoryResult>> findListHistory(MemberAuth memberAuth) {
        List<OddiHistoryResult> oddiRes = oddiService.findListHistory(memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiRes);
    }

    @ApiOperation(value = "오디존 사용가능 슬롯 조회 API", notes = "오디존 사용가능 슬롯 조회 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/oddi/adv/partner/slot")
    public ApiResponseDto<OddiReadyPartnerSlotSearchResult> findReadyPartnerSlot (
        AdvReadyPartnerSlotReq req) throws ParseException {
        OddiReadyPartnerSlotSearchResult oddiReadyPartnerSlotSearchResult =
            OddiReadyPartnerSlotSearchResult.builder().partnerSlotList(oddiService.findReadyPartnerSlotList(req)).build();

        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiReadyPartnerSlotSearchResult);
    }

    @ApiOperation(value = "광고사례 조회(파트너에게 매핑된 가장최근 VOD조회) API", notes = "광고사례 조회(파트너에게 매핑된 가장최근 VOD조회) API 입니다. ")
    @GetMapping(path = "/partner-vod/{partnerSeq}/{channelType}")
    public ApiResponseDto<Youtube> findPartnerVodInfo(@PathVariable Long partnerSeq, @PathVariable String channelType ) {
        Youtube youtube = oddiService.findVodInfo(partnerSeq, null, channelType);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, youtube);
    }

    @ApiOperation(value = "묶음 상품 광고사례 조회(묶음상품중 파트너의 가장최신 VOD조회) API", notes = "묶음 상품 광고사례 조회(묶음상품중 파트너의 가장최신 VOD조회) API 입니다. ")
    @GetMapping(path = "/product-vod/{productSeq}/{channelType}")
    public ApiResponseDto<Youtube> findProductVodInfo(@PathVariable Long productSeq, @PathVariable String channelType ) {
        Youtube youtube = oddiService.findVodInfo(null, productSeq, channelType);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, youtube);
    }

    @ApiOperation(value = "광고사례 가장최신 1건 VOD조회 API", notes = "광고사례 가장최신 1건 VOD조회 API 입니다. ")
    @GetMapping(path = "/adv-vod/{channelType}")
    public ApiResponseDto<Youtube> findTopVodInfo(@PathVariable String channelType) {
        Youtube youtube = oddiService.findTopVodInfo(channelType);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, youtube);
    }


    @ApiOperation(value = "매장명 리스트 API", notes = "매장명 리스트 API 입니다. ")
    @GetMapping(path = "/adv/name-list")
    public ApiResponseDto<PartnerName> findOddiNameList() {
        List<PartnerName> oddiNameList = oddiService.findOddiNameList();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, oddiNameList);
    }

}

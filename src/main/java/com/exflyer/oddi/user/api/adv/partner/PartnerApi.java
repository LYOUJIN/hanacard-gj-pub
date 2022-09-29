package com.exflyer.oddi.user.api.adv.partner;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.adv.partner.dto.PartnerJoinReq;
import com.exflyer.oddi.user.api.adv.partner.dto.PartnerRes;
import com.exflyer.oddi.user.api.adv.partner.service.PartnerService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.PartnerRequest;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "파트너신청", protocols = "http")
@Slf4j
@RestController
public class PartnerApi {

    @Autowired
    PartnerService partnerService;

    @ApiOperation(value = "파트너신청 사업자코드", notes = "파트너신청 사업자코드 API 입니다. ")
    @GetMapping(path = "/partner/codes")
    public ApiResponseDto<List<Code>> getCodes() {
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, partnerService.getBusinessCode());
    }

    @ApiOperation(value = "파트너 구별 목록", notes = "파트너 구별 조회 API입니다. ")
    @GetMapping(path = "/partner")
    public ApiResponseDto<List<PartnerRes>> getPartners() {
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, partnerService.getPartnerByAddr());
    }

    @ApiOperation(value = "파트너 신청 사용자 이름", notes = "파트너 신청 사용자 이름가져오기")
    @LoginNeedApi
    @GetMapping(path = "/partner/name")
    public ApiResponseDto<String> getName(MemberAuth memberAuth) throws ApiException{
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, partnerService.getMemberName(memberAuth.getId()));
    }

    @ApiOperation(value = "파트너 신청", notes = "파트너 신청")
    @PostMapping("/partner")
    public ApiResponseDto save(@Validated @RequestBody PartnerRequest req)throws ApiException{
        partnerService.save(req);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }

}

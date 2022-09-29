package com.exflyer.oddi.user.api.my;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.my.dto.MyAlarmResult;
import com.exflyer.oddi.user.api.my.dto.OddiMng;
import com.exflyer.oddi.user.api.my.dto.OddiMngResult;
import com.exflyer.oddi.user.api.my.service.OddiMngService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "마이페이지", protocols = "http")
@Slf4j
@RestController
public class OddiMngApi {

    @Autowired
    private OddiMngService oddiMngService;

    @ApiOperation(value = "오디관리 API", notes = "오디관리 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/my/oddi")
    public ApiResponseDto<PagingResult<OddiMngResult>> findList(@Validated @RequestBody OddiMng oddiMng, MemberAuth memberAuth) {
        oddiMng.setMemberId(memberAuth.getId());
        PagingResult res = oddiMngService.findList(oddiMng);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
    }

    @ApiOperation(value = "오디관리 알림확인 API", notes = "오디관리 알림확인 API 입니다. ")
    @LoginNeedApi
    @PutMapping(path = "/my/oddi/{advSeq}")
    public ApiResponseDto findOddiAlarm(@Validated @RequestBody Long advSeq, MemberAuth memberAuth) throws Exception{
        oddiMngService.findOddiAlarm(advSeq, memberAuth.getId(), true);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }

    @ApiOperation(value = "오디관리/1:1문의 알림 API", notes = "오디관리/1:1문의 알림 API 입니다. ")
    @LoginNeedApi
    @GetMapping(path = "/my/oddi")
    public ApiResponseDto<MyAlarmResult> findMyAlarm(MemberAuth memberAuth) throws Exception{
        MyAlarmResult res = oddiMngService.findMyAlarm(memberAuth.getId());
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
    }
}

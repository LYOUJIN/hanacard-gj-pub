package com.exflyer.oddi.user.api.test;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.test.service.TestService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @user : 2022-07-07
 * @test
 */

@Api(tags = "알림톡", protocols = "http")
@Slf4j
@RestController
public class TestApi {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "알림톡 즉시발송 테스트 API", notes = "알림톡 테스트 API 입니다.")
    @GetMapping(path = "/kakao/template/{templateId}")
    public ApiResponseDto findkakaoTemplate(@PathVariable String templateId) throws ApiException {

        testService.sendKakaoTemplate(templateId);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }

    @ApiOperation(value = "알림톡 테스트 API", notes = "알림톡 테스트 API 입니다.")
    @GetMapping(path = "/kakao/template-group/{templateId}")
    public ApiResponseDto findkakaoTemplateGroup(@PathVariable String templateId) throws ApiException {

        testService.sendGroupKakaoTemplate(templateId);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }
}

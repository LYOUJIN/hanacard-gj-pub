package com.exflyer.oddi.user.api.voc.terms;

import com.exflyer.oddi.user.api.voc.terms.dto.TermsServiceRes;
import com.exflyer.oddi.user.api.voc.terms.service.TermsService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "약관", protocols = "http")
@Slf4j
@RestController
public class TermsApi {

    @Autowired
    private TermsService termsService;

    @ApiOperation(value = "회원가입 약관 조회 ", notes = "회원가입 약관 조회 API 입니다.")
    @GetMapping(path = "/terms/{status}")
    public ApiResponseDto<TermsServiceRes> findTermsService(@PathVariable Long status) throws ApiException {
        List<TermsServiceRes> termsServiceRes = termsService.findTermsService(status);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, termsServiceRes);
    }

}

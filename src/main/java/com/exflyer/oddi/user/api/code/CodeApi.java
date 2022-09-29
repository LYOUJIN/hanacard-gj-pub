package com.exflyer.oddi.user.api.code;


import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.code.service.CodeService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.CodeGroup;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "코드", protocols = "http")
@Slf4j
@RestController
public class CodeApi {

    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "그룹 코드 목록 조회", notes = "그룹 코드 목록 조회 API 입니다. ")
    @GetMapping(path = "/codes/group")
    @LoginNeedApi
    public ApiResponseDto<List<CodeGroup>> findGroupCodeList() {
        List<CodeGroup> groupCodeList = codeService.findGroupCodeList();
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, groupCodeList);
    }

    @ApiOperation(value = "그룹 코드로 코드 목록 조회", notes = "그룹 코드로 코드 목록 조회 하는 API")
    @GetMapping(path = "/codes/{groupCode}")
    public ApiResponseDto<List<Code>> findCodeList(@PathVariable String groupCode) {
        List<Code> codeList = codeService.findCodeList(groupCode);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, codeList);
    }

}

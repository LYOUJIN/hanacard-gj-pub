package com.exflyer.oddi.user.api.my;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.my.dto.PaymentMng;
import com.exflyer.oddi.user.api.my.dto.PaymentMngResult;
import com.exflyer.oddi.user.api.my.service.PaymentMngService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "결제내역", protocols = "http")
@Slf4j
@RestController
public class PaymentMngApi {

    @Autowired
    private PaymentMngService paymentMngService;

    @ApiOperation(value = "결제내역 API", notes = "결제내역 API 입니다. ")
    @LoginNeedApi
    @PostMapping(path = "/my/payment")
    public ApiResponseDto<PagingResult<PaymentMngResult>> findList(@Validated @RequestBody PaymentMng paymentMng,MemberAuth memberAuth) {
        paymentMng.setMemberId(memberAuth.getId());
        PagingResult res = paymentMngService.findList(paymentMng);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
    }
}

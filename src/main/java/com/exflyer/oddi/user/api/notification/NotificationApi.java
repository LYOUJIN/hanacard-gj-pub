package com.exflyer.oddi.user.api.notification;

import com.exflyer.oddi.user.api.notification.dto.KakaoNotificationReq;
import com.exflyer.oddi.user.api.notification.dto.KakaoNotificationRes;
import com.exflyer.oddi.user.api.notification.service.KakaoNotificationService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "문자발송", protocols = "http")
@Slf4j
@RestController
public class NotificationApi {

    @Autowired
    private KakaoNotificationService kakaoNotificationService;

    @ApiOperation(value = "카카오톡 알림톡 전송", notes = "카카오톡 알림톡 전송 API 입니다. ")
    @GetMapping(path = "/notification")
    public ApiResponseDto<KakaoNotificationRes> getPlayItemAfterDBInsert(
        KakaoNotificationReq kakaoNotificationReq) throws ApiException, IOException {
        KakaoNotificationRes kakaoNotificationRes = kakaoNotificationService.reqNotification(kakaoNotificationReq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS, kakaoNotificationRes);
    }

    @ApiOperation(value = "결제 취소(광고취소) 알림톡 전송", notes = "결제 취소(광고취소) 알림톡 전송 API 입니다. ")
    @PutMapping(path = "/notification/paymentCancel/{advSeq}")
    public ApiResponseDto getKakaoPaymentCancel(@PathVariable Long advSeq) throws ApiException {
        kakaoNotificationService.sendKakaoAdvCancel(advSeq);
        return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }
}

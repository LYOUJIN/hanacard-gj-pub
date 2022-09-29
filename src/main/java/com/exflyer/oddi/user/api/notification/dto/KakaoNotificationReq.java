package com.exflyer.oddi.user.api.notification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KakaoNotificationReq {

    @ApiModelProperty(value = "국가번호를 포함한 전화번호(수신번호)", position = 1, required = true)
    private String to;

    @ApiModelProperty(value = "요청할 템플릿 내용", position = 2, hidden = true)
    private String text;

    @ApiModelProperty(value = "문자 재전송시 발신번호", hidden = true)
    private String from;

    @ApiModelProperty(value = "요청할 템플릿 코드명", position = 4, required = true)
    private String templateCode;

    @ApiModelProperty(value = "템플릿", position = 4, required = true)
    private String templateContents;

    @ApiModelProperty(value = "예약 발송시 예약시간(YYYYMMDDhhmm - 12자리), 미입력시 즉시전송", position = 5)
    private String reservedTime;

    @ApiModelProperty(value = "알림톡 전송 실패시 문자 재전송여부(Y:text의 문구전송, R:re_text의 문구전송)", position = 6)
    private String reSend;

    @ApiModelProperty(value = "알림톡 전송 실패시 재전송될 사용자 정의 메세지", position = 7)
    private String reText;

    @ApiModelProperty(value = "사용자ID", position = 7)
    private String regId;
}

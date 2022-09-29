package com.exflyer.oddi.user.api.notification.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KakaoMessageBody {

    @ApiModelProperty(value = "메세지 고유키값", hidden = true)
    private String message_id;

    @ApiModelProperty(value = "알림톡 전송 실패시 문자 재전송여부(Y:text의 문구전송, R:re_text의 문구전송)", position = 1)
    private String re_send;

    @ApiModelProperty(value = "국가번호를 포함한 전화번호(수신번호)", position = 2, required = true)
    private String to;

    @ApiModelProperty(value = "요청할 템플릿 코드명", position = 4, required = true)
    private String template_code;

    @ApiModelProperty(value = "문자 재전송시 발신번호", position = 5)
    private String from;

    @ApiModelProperty(value = "요청할 템플릿 내용", position = 6, required = true)
    private String text;

}

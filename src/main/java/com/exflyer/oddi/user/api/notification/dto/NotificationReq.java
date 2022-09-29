package com.exflyer.oddi.user.api.notification.dto;


import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotificationReq {

    @ApiModelProperty(value = "알림그룹seq", position = 1)
    private Long groupSeq;

    @ApiModelProperty(value = "회원id", position = 1)
    private String memberId;

    @ApiModelProperty(value = "회원이름", position = 1)
    private String memberName;

    @ApiModelProperty(value = "내용", position = 1)
    private String contents;

    @ApiModelProperty(value = "광고 메세지 여부", position = 1)
    private Integer advMessage;

    @ApiModelProperty(value = "발송 시간(즉시 일경우 0)", position = 1)
    private String sendTime;

    @ApiModelProperty(value = "발신자 전화번호", position = 1)
    private String senderPhoneNumber;

    @ApiModelProperty(value = "발신자", position = 1)
    private String senderName;

    @ApiModelProperty(value = "자동 여부", position = 1)
    private Integer auto = 0;

    @ApiModelProperty(value = "생성 날짜", position = 1)
    private LocalDateTime regDate;

    @ApiModelProperty(value = "생성 id", position = 1)
    private String regId;

    @ApiModelProperty(value = "알림톡 여부", position = 1)
    private Boolean alrimTalk;

    @ApiModelProperty(value = "카카오 템플릿 ID", position = 1)
    private String kakaoTemplateId;


}

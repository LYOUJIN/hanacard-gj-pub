package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.api.mustad.dto.MustadNotificationReq;
import com.exflyer.oddi.user.api.notification.dto.KakaoNotificationReq;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 알림_group
 */
@Data
@Entity
@ApiModel("알림_group")
@Table(name = "notification_group")
@NoArgsConstructor
public class NotificationGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @Id
    @ApiModelProperty("순번")
    @Column(name = "seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;


    /**
     * 타겟그룹순번
     */
    @ApiModelProperty("타겟그룹순번")
    @Column(name = "target_group_seq")
    private Long targetGroupSeq;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "contents")
    private String contents;

    /**
     * 광고 메세지 여부
     */
    @Column(name = "adv_message")
    @ApiModelProperty("광고 메세지 여부")
    private boolean advMessage;

    /**
     * 발송 시간(즉시 일경우 0)
     */
    @Column(name = "send_time")
    @ApiModelProperty("발송 시간(즉시 일경우 0)")
    private Boolean sendTime;

    /**
     * 발신자 전화 번호
     */
    @Column(name = "sender_phone_number")
    @DecryptField
    @ApiModelProperty("발신자 전화 번호")
    private String senderPhoneNumber;


    /**
     * 자동 여부
     */
    @ApiModelProperty("자동 여부")
    @Column(name = "auto", nullable = false)
    private boolean auto;

    /**
     * 완료여부
     */
    @ApiModelProperty("완료여부")
    @Column(name = "done", nullable = false)
    private boolean done;

    /**
     * 알림톡
     */
    @ApiModelProperty("알림톡")
    @Column(name = "alrim_talk", nullable = false)
    private Boolean alrimTalk;

    /**
     * 생성 id
     */
    @ApiModelProperty("생성 id")
    @Column(name = "reg_id")
    private String regId;

    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    /**
     * 템플릿코드
     */
    @ApiModelProperty("템플릿코드")
    @Column(name = "template_id")
    private String templateId;

    /**
     * 대상생성완료
     */
    @ApiModelProperty("대상생성완료")
    @Column(name = "target_create_done")
    private Boolean target_create_done = false;



    public NotificationGroup(KakaoNotificationReq kakaoNotificationReq) {
        this.targetGroupSeq = Long.valueOf(0);
        this.contents = kakaoNotificationReq.getTemplateContents();
        this.advMessage = false;
        this.auto = false;
        this.senderPhoneNumber = kakaoNotificationReq.getTo();
        this.done = false;
        this.regId = kakaoNotificationReq.getRegId();
        this.regDate = LocalDateUtils.krNow();
        this.templateId = kakaoNotificationReq.getTemplateCode();
        this.sendTime = false;
    }

    public void setNotificationGroup(MustadNotificationReq param) {
        this.targetGroupSeq = param.getTargetGroupSeq();
        this.contents = param.getContents();
        this.advMessage = param.getAdvMessage();
        this.senderPhoneNumber = param.getSendPhoneNumber();
        this.auto = param.getAuto();
        this.regDate = LocalDateUtils.krNow();
        this.regId = param.getRegId();
        this.done = param.getDone();
        this.alrimTalk = param.getAlrimTalk();
        this.target_create_done = param.getTargetCreateDone();
    }
}

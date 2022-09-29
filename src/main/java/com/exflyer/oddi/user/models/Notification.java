package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.annotaions.DecryptField;
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

/**
 * 알림
 */
@Data
@Entity
@ApiModel("알림")
@Table(name = "notification")
public class Notification implements Serializable {

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
   * 그룹 순번
   */
  @ApiModelProperty("그룹 순번")
  @Column(name = "group_seq", nullable = false)
  private Long groupSeq;

  /**
   * 수신자 ID
   */
  @ApiModelProperty("수신자 ID")
  @Column(name = "receive_id")
  private String receiveId;

  /**
   * 수신자 이름
   */
  @ApiModelProperty("수신자 이름")
  @Column(name = "receive_name", nullable = false)
  private String receiveName;

  /**
   * 수신자 전화 번호
   */
  @ApiModelProperty("수신자 전화 번호")
  @Column(name = "receive_phone_number", nullable = false)
  @DecryptField
  private String receivePhoneNumber;

  /**
   * 내용
   */
  @ApiModelProperty("내용")
  @Column(name = "contents", nullable = false)
  private String contents;

  /**
   * 발송 시간(즉시 일경우 0)
   */
  @ApiModelProperty("발송 시간(즉시 일경우 0)")
  @Column(name = "send_time", nullable = false)
  private boolean sendTime;

  /**
   * 발신자 id
   */
  @ApiModelProperty("발신자 id")
  @Column(name = "sender_id", nullable = false)
  private String senderId;

  /**
   * 발신자 이름
   */
  @ApiModelProperty("발신자 이름")
  @Column(name = "sender_name", nullable = false)
  private String senderName;

  /**
   * 발신자 전화 번호
   */
  @ApiModelProperty("발신자 전화 번호")
  @Column(name = "sender_phone_number")
  private String senderPhoneNumber;


  /**
   * 카카오 템플릿 ID
   */
  @ApiModelProperty("카카오 템플릿 ID")
  @Column(name = "kakao_template_id")
  private String kakaoTemplateId;

  /**
   * 알림톡 여부
   */
  @ApiModelProperty("알림톡 여부")
  @Column(name = "alrim_talk")
  private Boolean alrimTalk;

  /**
   * 등록일자
   */
  @ApiModelProperty("등록일자")
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  private Boolean fail;

  private String response;

  public Notification(String message, String phoneNumber,String sendName) {
    this(message, phoneNumber, 0L,sendName);
  }

  public Notification(String message, String phoneNumber, Long groupSeq, String sendName) {
    this.groupSeq = groupSeq;
    this.alrimTalk = true;
    this.receivePhoneNumber = phoneNumber;
    this.contents = message;
    this.sendTime = false;
    this.senderId = "user-api";
    this.senderName = sendName;
    this.alrimTalk = false;
    this.regDate = LocalDateUtils.krNow();
  }

  public Notification() {
    this.groupSeq = 0L;
  }


  public Notification(KakaoNotificationReq kakaoNotificationReq) {
    this(kakaoNotificationReq, 0L);
  }

  public Notification(KakaoNotificationReq kakaoNotificationReq, Long groupSeq) {
    this.groupSeq = groupSeq;
    this.alrimTalk = true;
    this.receivePhoneNumber = kakaoNotificationReq.getTo();
    this.contents = kakaoNotificationReq.getTemplateContents();
    this.sendTime = false;
    this.senderId = "USER-API";
    this.senderName = "USER-API";
    this.kakaoTemplateId = kakaoNotificationReq.getTemplateCode();
    this.alrimTalk = true;
    this.regDate = LocalDateUtils.krNow();
  }
}

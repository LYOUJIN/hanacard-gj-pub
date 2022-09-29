package com.exflyer.oddi.user.models;


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
 * 알림 이력
 */
@Data
@Entity
@ApiModel("알림 이력")
@Table(name = "notification_history")
public class NotificationHistory implements Serializable {

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
    private String receivePhoneNumber;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "contents", nullable = false)
    private String contents;

    /**
     * 발송 시간
     */
    @ApiModelProperty("발송 시간")
    @Column(name = "send_time", nullable = false)
    private String sendTime;

    /**
     * 요청 시간
     */
    @ApiModelProperty("요청 시간")
    @Column(name = "request_time", nullable = false)
    private String requestTime;

    /**
     * 응답 코드
     */
    @ApiModelProperty("응답 코드")
    @Column(name = "response_code", nullable = false)
    private String responseCode;

    /**
     * 성공 여부
     */
    @ApiModelProperty("성공 여부")
    @Column(name = "success", nullable = false)
    private Integer success = 1;

    /**
     * 발신자 이름
     */
    @ApiModelProperty("발신자 이름")
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

    @ApiModelProperty("발신자 전화 번호")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
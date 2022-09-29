package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.mustad.dto.MustadNotificationReq;
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
 * 알림 대상 그룹
 */
@Data
@Entity
@ApiModel("알림 대상 그룹")
@Table(name = "notification_target_group")
@NoArgsConstructor
public class NotificationTargetGroup implements Serializable {

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
   * 이름
   */
  @ApiModelProperty("이름")
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * 사용 여부
   */
  @ApiModelProperty("사용 여부")
  @Column(name = "usable", nullable = false)
  private Boolean usable = true;

  /**
   * 고정 여부
   */
  @ApiModelProperty("고정 여부")
  @Column(name = "fixed", nullable = false)
  private Boolean fixed = true;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  /**
   * 생성 id
   */
  @ApiModelProperty("생성 id")
  @Column(name = "reg_id", nullable = false)
  private String regId;

  /**
   * 변경 날짜
   */
  @Column(name = "mod_date")
  @ApiModelProperty("변경 날짜")
  private LocalDateTime modDate;

  /**
   * 변경 id
   */
  @Column(name = "mod_id")
  @ApiModelProperty("변경 id")
  private String modId;

  /**
   * 발송 대상 코드
   */
  @Column(name = "target_code")
  @ApiModelProperty("발송 대상 코드")
  private String targetCode;

  /**
   * 발송 시간
   */
  @Column(name = "send_time")
  @ApiModelProperty("발송 시간")
  private String sendTime;

  public NotificationTargetGroup(MustadNotificationReq param){
    this.name = param.getName();
    this.usable = true;
    this.fixed = false;
    this.regDate = LocalDateUtils.krNow();
    this.regId = param.getRegId();
    this.targetCode = param.getTargetCode();
  }

}

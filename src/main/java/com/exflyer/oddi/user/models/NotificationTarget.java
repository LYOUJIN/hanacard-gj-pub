package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.mustad.dto.MustadNotificationReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 알림 대상
 */
@Data
@Entity
@ApiModel("알림 대상")
@Table(name = "notification_target")
@NoArgsConstructor
public class NotificationTarget implements Serializable {

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
   * 알림대상 그룹순번
   */
  @ApiModelProperty("알림대상 그룹순번")
  @Column(name = "target_group_seq", nullable = false)
  private Long targetGroupSeq;

  /**
   * 회원 id
   */
  @ApiModelProperty("회원 id")
  @Column(name = "member_id")
  private String memberId;

  /**
   * 회원 이름
   */
  @ApiModelProperty("회원 이름")
  @Column(name = "name")
  private String name;

  /**
   * 전화번호
   */
  @ApiModelProperty("전화번호")
  @Column(name = "phone_number")
  private String phoneNumber;

  /**
   * 파트너 순번
   */
  @ApiModelProperty("파트너 순번")
  @Column(name = "partner_seq", nullable = false)
  private Long partnerSeq;

  public NotificationTarget(MustadNotificationReq param){
    this.targetGroupSeq = param.getTargetGroupSeq();
    this.memberId = param.getMemberId();
    this.name = param.getName();
    this.phoneNumber = param.getSendPhoneNumber();
  }

}

package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 * 회원 프로모션
 */
@Data
@Entity
@ApiModel("회원 프로모션")
@Table(name = "member_promotion")
public class MemberPromotion implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private MemberPromotionPk memberPromotionPk;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  /**
   * 사용 여부
   */
  @ApiModelProperty("사용 여부")
  @Column(name = "usable", nullable = false)
  private Boolean usable = true;

}

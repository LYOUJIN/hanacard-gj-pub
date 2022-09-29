package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * 회원 프로모션
 */
@Data
@Embeddable
public class MemberPromotionPk implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 회원 id
   */
  @ApiModelProperty("회원 id")
  @Column(name = "member_id", nullable = false)
  private String memberId;

  /**
   * 홍보 순번
   */
  @ApiModelProperty("홍보 순번")
  @Column(name = "promotion_seq", nullable = false)
  private Long promotionSeq;

}

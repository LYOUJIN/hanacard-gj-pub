package com.exflyer.oddi.user.models;

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
 * 프로모션쿠폰
 */
@Data
@Entity
@NoArgsConstructor
@ApiModel("프로모션쿠폰")
@Table(name = "promotion_coupon")
public class PromotionCoupon implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @ApiModelProperty("순번")
  @Column(name = "seq", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @ApiModelProperty("홍보 순번")
  @Column(name = "promotion_seq")
  private Long promotionSeq;

  @ApiModelProperty("쿠폰 코드")
  @Column(name = "coupon_code")
  private String couponCode;

  @ApiModelProperty("사용")
  @Column(name = "usable")
  private Boolean usable;

  @ApiModelProperty("회원 id")
  @Column(name = "member_id")
  private String memberId;

  @ApiModelProperty("사용 날짜")
  @Column(name = "using_date")
  private LocalDateTime usingDate;

  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  @ApiModelProperty("만료 날짜")
  @Column(name = "expired_date")
  private String expiredDate;

  public PromotionCoupon(Promotion promotion, String memberId, Boolean usable) {
    this.promotionSeq = promotion.getSeq();
    this.usable = usable;
    this.memberId = memberId;
    this.regDate = LocalDateUtils.krNow();
    if(promotion.getMemberCouponExpiredDay() != null) {
      this.expiredDate = LocalDateUtils.addkrNow(promotion.getMemberCouponExpiredDay());
    }

  }

}

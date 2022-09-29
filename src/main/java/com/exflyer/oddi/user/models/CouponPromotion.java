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
 * 프로모션
 */
@Data
@Entity
@ApiModel("프로모션")
@Table(name = "coupon_promotion")
public class CouponPromotion implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 순번
   */
  @Id
  @ApiModelProperty("순번")
  @Column(name = "seq", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @ApiModelProperty("이름")
  @Column(name = "name")
  private String name;

  @ApiModelProperty("종류(가입, 일반 등)")
  @Column(name = "type")
  private String type;

  @ApiModelProperty("할인 종류(정액, 정률)")
  @Column(name = "discount_type")
  private String discountType;

  @ApiModelProperty("할인 금액")
  @Column(name = "discount_price")
  private Long discountPrice;

  @ApiModelProperty("내용")
  @Column(name = "contents")
  private String contents;

  @ApiModelProperty("사용 여부")
  @Column(name = "usable")
  private Boolean usable;

  @ApiModelProperty("가입자 쿠폰 가입후 만료일자")
  @Column(name = "signup_coupon_expired_day")
  private Integer signupCouponExpiredDay;

  @ApiModelProperty("가입자 쿠폰 사용기한(첫결제시,가입시)")
  @Column(name = "signup_coupon_type")
  private String signupCouponType;

  @ApiModelProperty("다중사용 가능여부")
  @Column(name = "multi_using")
  private String multiUsing;

  @ApiModelProperty("프로모션 채널 종류")
  @Column(name = "promotion_channel_type")
  private String promotionChannelType;

  @ApiModelProperty("선착순 여부(0:무제한,1:선착순")
  @Column(name = "coupon_limit")
  private Boolean couponLimit;

  @ApiModelProperty("쿠폰사용가능횟수")
  @Column(name = "usable_count")
  private Integer usableCount;

  @ApiModelProperty("시작 날짜")
  @Column(name = "start_date")
  private String startDate;

  @ApiModelProperty("만료 날짜")
  @Column(name = "expired_date")
  private String expiredDate;

  @ApiModelProperty("생성 id")
  @Column(name = "reg_id")
  private String regId;

  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  @ApiModelProperty("변경 id")
  @Column(name = "mod_id")
  private String modId;

  @ApiModelProperty("변경 날짜")
  @Column(name = "mod_date")
  private LocalDateTime modDate;

}

package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name = "promotion")
public class Promotion implements Serializable {

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

  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  @ApiModelProperty("생성 id")
  @Column(name = "reg_id")
  private String regId;

  @ApiModelProperty("변경 날짜")
  @Column(name = "mod_date")
  private LocalDateTime modDate;

  @ApiModelProperty("변경 id")
  @Column(name = "mod_id")
  private String modId;

  @ApiModelProperty("가입자 쿠폰 가입후 만료일자")
  @Column(name = "member_coupon_expired_day")
  private Integer memberCouponExpiredDay;

  @ApiModelProperty("가입자 쿠폰 사용기한(첫결제시,가입시)")
  @Column(name = "member_coupon_code")
  private String memberCouponCode;



}

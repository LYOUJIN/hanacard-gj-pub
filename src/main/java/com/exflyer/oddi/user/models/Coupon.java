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
 * 프로모션 쿠폰
 */
@Data
@Entity
@NoArgsConstructor
@ApiModel("프로모션 쿠폰")
@Table(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("순번")
    @Column(name = "seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ApiModelProperty("프로모션 순번")
    @Column(name = "promotion_seq")
    private Long promotionSeq;

    @ApiModelProperty("쿠폰코드")
    @Column(name = "coupon_code")
    private String couponCode;

    @ApiModelProperty("사용 여부")
    @Column(name = "usable")
    private Boolean usable;

    @ApiModelProperty("쿠폰사용횟수")
    @Column(name = "coupon_count")
    private int couponCount;

    @ApiModelProperty("시작 날짜")
    @Column(name = "start_date")
    private String startDate;

    @ApiModelProperty("만료 날짜")
    @Column(name = "expired_date")
    private String expiredDate;

    @ApiModelProperty("삭제여부")
    @Column(name = "del")
    private Boolean del;

    @ApiModelProperty("생성id")
    @Column(name = "reg_id")
    private String regId;

    @ApiModelProperty("생성 날짜")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public Coupon(CouponPromotion promotion, String couponCode, int couponCount, String memberId) {
        this.promotionSeq = promotion.getSeq();
        this.couponCode = couponCode;
        this.usable = false;
        this.couponCount = couponCount;
        this.startDate = promotion.getStartDate();
        this.expiredDate = promotion.getExpiredDate();
        this.del = false;
        this.regId = memberId;
        this.regDate = LocalDateUtils.krNow();
    }
}

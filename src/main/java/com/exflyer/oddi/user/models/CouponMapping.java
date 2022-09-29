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
 * 프로모션 쿠폰 매핑
 */
@Data
@Entity
@NoArgsConstructor
@ApiModel("프로모션 쿠폰 그룹")
@Table(name = "coupon_mapping")
public class CouponMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("순번")
    @Column(name = "seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ApiModelProperty("프로모션 쿠폰 순번")
    @Column(name = "coupon_seq")
    private Long couponSeq;

    @ApiModelProperty("회원 id")
    @Column(name = "member_id")
    private String memberId;

    @ApiModelProperty("사용 여부")
    @Column(name = "usable")
    private Boolean usable;

    @ApiModelProperty("사용 날짜")
    @Column(name = "using_date")
    private LocalDateTime usingDate;

    @ApiModelProperty("결제 순번")
    @Column(name = "payment_seq")
    private Long paymentSeq;

    @ApiModelProperty("생성 날짜")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public CouponMapping(Coupon coupon, String memberId) {
        this.couponSeq = coupon.getSeq();
        this.memberId = memberId;
        this.usable = false;
        this.regDate = LocalDateUtils.krNow();
    }

}

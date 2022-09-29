package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@ApiModel("광고")
@Table(name="adv")
@NoArgsConstructor
public class Adv implements Serializable {

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
     * 회원id
     */
    @ApiModelProperty("회원id")
    @Column(name = "member_id")
    private String memberId;

    /**
     * 제목
     */
    @ApiModelProperty("title")
    @Column(name = "title")
    private String title;

    /**
     * 업종 종류 코드
     */
    @ApiModelProperty("업종 종류 코드 ")
    @Column(name = "business_type_code")
    private String businessTypeCode;

    @ApiModelProperty("기타 업종명")
    @Column(name = "etc_business_name")
    private String etcBusinessName;

    /**
     * 시작 날짜
     */
    @ApiModelProperty("시작 날짜")
    @Column(name = "start_date")
    private String startDate;

    /**
     * 종료 날짜
     */
    @ApiModelProperty("종료 날짜")
    @Column(name = "end_date")
    private String endDate;

    /**
     * 금액
     */
    @ApiModelProperty("금액")
    @Column(name = "price")
    private Integer price;

    /**
     * 할인 금액
     */
    @ApiModelProperty("할인 금액")
    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    /**
     * 생성 날짜
     */
    @ApiModelProperty("생성 날짜")
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    /**
     * 변경 날짜
     */
    @ApiModelProperty("변경 날짜")
    @Column(name = "mod_date")
    private LocalDateTime modDate;

    /**
     * 심사 코드
     */
    @ApiModelProperty("심사 코드")
    @Column(name = "audit_code")
    private String auditCode;

    /**
     * 진행 코드(심사중, 결제전, 결제후 등)
     */
    @ApiModelProperty("진행 코드(심사중, 결제전, 결제후 등)")
    @Column(name = "progress_code")
    private String progressCode;

    /**
     * 쿠폰 번호
     */
    @ApiModelProperty("쿠폰 번호")
    @Column(name = "coupon_number")
    private String couponNumber;

    /**
     * 결제 순번
     */
    @ApiModelProperty("결제 순번")
    @Column(name = "payment_seq")
    private Long paymentSeq;

    /**
     * 거절 이유
     */
    @ApiModelProperty("거절 이유")
    @Column(name = "rejection_reason")
    private String rejectionReason;

    /**
     * 거절 코드
     */
    @ApiModelProperty("거절 코드")
    @Column(name = "rejection_code")
    private String rejectionCode;

    /**
     * 거절 날짜
     */
    @ApiModelProperty("거절 날짜")
    @Column(name = "rejection_date")
    private Timestamp rejectionDate;

    /**
     * 심사 id
     */
    @ApiModelProperty("심사 id")
    @Column(name = "audit_id")
    private String auditId;

    /**
     * 디자인 요청 여부
     */
    @ApiModelProperty("디자인 요청 여부")
    @Column(name = "design_request")
    private Boolean designRequest;

    /**
     * 채널 종류(오디존, 지하철)
     */
    @ApiModelProperty("채널 종류(오디존, 지하철)")
    @Column(name = "channel_type")
    private String channelType;

    /**
     * 승인 날짜
     */
    @ApiModelProperty("승인 날짜")
    @Column(name = "approval_date")
    private Timestamp approvalDate;

    /**
     * 메모
     */
    @ApiModelProperty("메모")
    @Column(name = "memo")
    private String memo;

    /**
     * 발송코드
     */
    @ApiModelProperty("발송 코드(미발송, 승인, 보류, 종료예정))")
    @Column(name = "send_code")
    private String sendCode;

    /**
     * 회원 회사순번
     */
    @ApiModelProperty("회원 회사순번")
    @Column(name = "company_seq")
    private Long companySeq;

    /**
     * 광고 신청취소 가능일
     */
    @ApiModelProperty("광고 신청취소 가능일")
    @Column(name = "oddi_adv_cancel_date")
    private int oddiAdvCancelDate;


    /**
     * 사용자 알림 확인여부
     */
    @ApiModelProperty("사용자 알림 확인여부")
    @Column(name = "user_check")
    private Boolean userCheck;

    @ApiModelProperty("관리자 알림톡 발송 여부")
    @Column(name = "manager_send_done")
    private Boolean managerSendDone = false;

    public Adv(AdvAddReq advAddReq) {
        this.memberId = advAddReq.getRegId();
        this.title = advAddReq.getTitle();
        this.businessTypeCode = advAddReq.getBusinessTypeCode();
        this.etcBusinessName = advAddReq.getEtcBusinessName();
        this.startDate = advAddReq.getStartDate();
        this.endDate = advAddReq.getEndDate();
        this.designRequest = advAddReq.getDesignRequest();
        this.regDate = advAddReq.getRegDate();
        this.channelType = advAddReq.getChannelType();
        this.rejectionCode = advAddReq.getRejectionCode();
        this.oddiAdvCancelDate = advAddReq.getOddiAdvCancelDate();
        this.userCheck = advAddReq.getUserCheck();
        this.progressCode = advAddReq.getProgressCode();
        this.price = advAddReq.getPrice();
    }
}

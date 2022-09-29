package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@ApiModel("파트너 설정")
@Table(name = "partner_config")
public class PartnerConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 슬롯 카운트
     */
    @ApiModelProperty("슬롯 카운트")
    @Column(name = "slot_count", nullable = false)
    private Integer slotCount;

    /**
     * 슬롯 영상 시간
     */
    @ApiModelProperty("슬롯 영상 시간")
    @Column(name = "slot_video_time")
    private BigDecimal slotVideoTime;

    /**
     * 디자인 요청 여부
     */
    @ApiModelProperty("디자인 요청 여부")
    @Column(name = "design_request")
    private Boolean designRequest;

    /**
     * 화면 분할(1, 2, 3분할)
     */
    @Column(name = "display_div")
    @ApiModelProperty("화면 분할(1, 2, 3분할)")
    private String displayDiv;

    /**
     * 측면 화면 서비스 코드
     */
    @ApiModelProperty("측면 화면 서비스 코드")
    @Column(name = "side_display_service_code")
    private String sideDisplayServiceCode;

    /**
     * 하단 화면 서비스 코드
     */
    @ApiModelProperty("하단 화면 서비스 코드")
    @Column(name = "bottom_display_service_code")
    private String bottomDisplayServiceCode;

    /**
     * 종류(오디존, 지하철)
     */
    @Id
    @Column(name = "type")
    @ApiModelProperty("종류(오디존, 지하철)")
    private String type;

    /**
     * 운영시작시간
     */
    @Column(name = "operation_start_time")
    @ApiModelProperty("운영시작시간")
    private String operationStartTime;

    /**
     * 운영종료시간
     */
    @Column(name = "operation_end_time")
    @ApiModelProperty("운영종료시간")
    private String operationEndTime;

    /**
     * 오디존 광고 시작 가능일
     */
    @Column(name = "oddi_adv_from_start_date")
    @ApiModelProperty("오디존 광고 시작 가능일")
    private String oddiAdvFromStartDate;

    /**
     * 오디존 광고 시작 가능일
     */
    @Column(name = "oddi_adv_to_start_date")
    @ApiModelProperty("오디존 광고 시작 가능일")
    private String oddiAdvToStartDate;

    /**
     * 오디존 최장 광고기간
     */
    @Column(name = "oddi_adv_max_date")
    @ApiModelProperty("오디존 최장 광고기간")
    private String oddiAdvMaxDate;

    /**
     * 오디존 광고 신청취소 가능일
     */
    @Column(name = "oddi_adv_cancel_date")
    @ApiModelProperty("오디존 광고 신청취소 가능일")
    private String oddiAdvCancelDate;

    /**
     * 지하철 익월 광고 신청 마감일
     */
    @Column(name = "subway_adv_last_date")
    @ApiModelProperty("지하철 익월 광고 신청 마감일")
    private String subwayAdvLastDate;

    /**
     * 지하철 최대광고 시작일
     */
    @Column(name = "subway_adv_max_start_date")
    @ApiModelProperty("지하철 최대광고 시작일")
    private String subwayAdvMaxStartDate;

    /**
     * 지하철 광고 신청취소 가능일
     */
    @Column(name = "subway_adv_cancel_date")
    @ApiModelProperty("지하철 광고 신청취소 가능일")
    private String subwayAdvCancelDate;

    /**
     * 지하철 최장 광고기간
     */
    @Column(name = "subway_adv_max_date")
    @ApiModelProperty("지하철 최장 광고기간")
    private String subwayAdvMaxDate;

}


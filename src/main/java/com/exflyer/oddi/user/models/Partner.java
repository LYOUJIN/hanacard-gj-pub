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
import lombok.NoArgsConstructor;

/**
 * 파트너
 */
@Data
@Entity
@ApiModel("파트너")
@Table(name = "partner")
@NoArgsConstructor
public class Partner implements Serializable {

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
   * 종류(오디존, 지하철)
   */
  @Column(name = "channel_type")
  @ApiModelProperty("종류(오디존, 지하철)")
  private String channelType;

  /**
   * 매장 이름
   */
  @ApiModelProperty("매장 이름")
  @Column(name = "mall_name")
  private String mallName;

  /**
   * 우편번호
   */
  @Column(name = "zipcode")
  @ApiModelProperty("우편번호")
  private String zipcode;

  /**
   * 주소
   */
  @Column(name = "addr")
  @ApiModelProperty("주소")
  private String addr;

  /**
   * 지번주소
   */
  @Column(name = "old_addr")
  @ApiModelProperty("지번주소")
  private String oldAddr;

  /**
   * 상세 주소
   */
  @ApiModelProperty("상세 주소")
  @Column(name = "detail_addr")
  private String detailAddr;

  /**
   * 소유자 이름
   */
  @ApiModelProperty("소유자 이름")
  @Column(name = "owner_name")
  private String ownerName;

  /**
   * 소유자 전화 번호
   */
  @ApiModelProperty("소유자 전화 번호")
  @Column(name = "owner_phone_number")
  private String ownerPhoneNumber;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  /**
   * 생성 id
   */
  @ApiModelProperty("생성 id")
  @Column(name = "reg_id", nullable = false)
  private String regId;

  /**
   * 변경 날짜
   */
  @ApiModelProperty("변경 날짜")
  @Column(name = "mod_date")
  private LocalDateTime modDate;

  /**
   * 이름
   */
  @ApiModelProperty("이름")
  @Column(name = "mod_id")
  private String modId;

  /**
   * 설명
   */
  @ApiModelProperty("설명")
  @Column(name = "description")
  private String description;

  /**
   * 운영 여부
   */
  @ApiModelProperty("운영 여부")
  @Column(name = "operation")
  private Boolean operation;

  /**
   * 메모
   */
  @Column(name = "memo")
  @ApiModelProperty("메모")
  private String memo;

  /**
   * 총 슬롯
   */
  @ApiModelProperty("총 슬롯")
  @Column(name = "total_slot", nullable = false)
  private Integer totalSlot;

  /**
   * 슬롯당 노출 시간
   */
  @ApiModelProperty("슬롯당 노출 시간")
  @Column(name = "slot_video_time", nullable = false)
  private Integer slotVideoTime;

  /**
   * 슬롯당 금액
   */
  @ApiModelProperty("슬롯당 금액")
  @Column(name = "slot_price", nullable = false)
  private Integer slotPrice;

  /**
   * 운영 요일
   */
  @ApiModelProperty("운영 요일")
  @Column(name = "operation_week")
  private String operationWeek;

  /**
   * 운영 시작 시간
   */
  @ApiModelProperty("운영 시작 시간")
  @Column(name = "operation_start_time")
  private String operationStartTime;

  /**
   * 운영 종료 시간
   */
  @ApiModelProperty("운영 종료 시간")
  @Column(name = "operation_end_time")
  private String operationEndTime;

  /**
   * 일 노출 카운트
   */
  @ApiModelProperty("일 노출 카운트")
  @Column(name = "day_expo_count", nullable = false)
  private Integer dayExpoCount;

  /**
   * 위치(지하철 광고 위치)
   */
  @Column(name = "location")
  @ApiModelProperty("위치(지하철 광고 위치)")
  private String location;

  /**
   * 화면(지하철 광고 화면)
   */
  @Column(name = "display")
  @ApiModelProperty("화면(지하철 광고 화면)")
  private String display;

  /**
   * 운영 종료 익일 구분
   */
  @Column(name = "operation_end_tomorrow_gbn")
  @ApiModelProperty("운영 종료 익일 구분")
  private Boolean operationEndTomorrowGbn;

  /**
   * 도면 파일순번
   */
  @Column(name = "map_file_seq")
  @ApiModelProperty("도면 파일순번")
  private Long mapFileSeq;

  /**
   * 요약
   */
  @ApiModelProperty("요약")
  @Column(name = "summary")
  private String summary;

  /**
   * 배지 코드
   */
  @ApiModelProperty("배지 코드")
  @Column(name = "badge_code")
  private String badgeCode;

  /**
   * 광고 사례 노출 여부
   */
  @Column(name = "adv_case_expo")
  @ApiModelProperty("광고 사례 노출 여부")
  private Boolean advCaseExpo;

  /**
   * 위도
   */
  @ApiModelProperty("위도")
  @Column(name = "latitude")
  private Double latitude;

  /**
   * 경도
   */
  @ApiModelProperty("경도")
  @Column(name = "longitude")
  private Double longitude;

  /**
   * X좌표
   */
  @ApiModelProperty("X좌표")
  @Column(name = "grid_x")
  private Double gridX;

  /**
   * Y좌표
   */
  @ApiModelProperty("Y좌표")
  @Column(name = "grid_y")
  private Double gridY;

  /**
   * 주소_시, 군
   */
  @ApiModelProperty("주소_시, 군")
  @Column(name = "addr_si")
  private String addrSi;

  /**
   * 주소_구
   */
  @ApiModelProperty("주소_구")
  @Column(name = "addr_gu")
  private String addrGu;

  /**
   * 주소_동,읍,리
   */
  @ApiModelProperty("주소_동,읍,리")
  @Column(name = "addr_dong")
  private String addrDong;

}

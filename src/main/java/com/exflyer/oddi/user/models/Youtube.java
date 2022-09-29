package com.exflyer.oddi.user.models;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Youtube
 */
@Data
@Entity
@ApiModel("youtube")
@Table(name = "youtube")
@NoArgsConstructor
public class Youtube implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 유튜브 동영상id
   */
  @Id
  @ApiModelProperty("유튜브 동영상id")
  @Column(name = "youtube_id", nullable = false)
  private String youtubeId;

  /**
   * 파트너 순번
   */
  @ApiModelProperty("파트너 순번")
  @Column(name = "partner_seq")
  private Long partnerSeq;

  /**
   * 유튜브 동영상 등록일
   */
  @ApiModelProperty("유튜브 동영상 등록일")
  @Column(name = "youtube_reg_date")
  private String youtubeRegDate;

  /**
   * 유튜브 제목
   */
  @ApiModelProperty("유튜브 제목")
  @Column(name = "youtube_title")
  private String youtubeTitle;

  /**
   * 유튜브 설명
   */
  @ApiModelProperty("유튜브 설명")
  @Column(name = "youtube_description")
  private String youtubeDescription;

  /**
   * 유튜브 썸네일 정보
   */
  @ApiModelProperty("유튜브 썸네일 정보")
  @Column(name = "youtube_thumbnails")
  private String youtubeThumbnails;

  /**
   * 유튜브 재생ID
   */
  @ApiModelProperty("유튜브 재생ID")
  @Column(name = "youtube_play_id")
  private String youtubePlayId;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date", nullable = false)
  private String regDate;

  /**
   * 유튜브 플레이 경로
   */
  @ApiModelProperty("유튜브 플레이 경로")
  @Column(name = "youtube_url", nullable = false)
  private String youtubeUrl;

  /**
   * 노출여부
   */
  @ApiModelProperty("노출여부")
  @Column(name = "expo")
  private Boolean expo;

}

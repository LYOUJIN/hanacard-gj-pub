package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 라이브 스트리밍
 */
@Data
@Entity
@ApiModel("라이브 스트리밍")
@Table(name = "live_streaming")
public class LiveStreaming implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 라이브스트림 채널ID
   */
  @Id
  @ApiModelProperty("라이브스트림 채널ID")
  @Column(name = "live_stream_channel_id")
  private String liveStreamChannelId;

  /**
   * 라이브채널 대표명
   */
  @ApiModelProperty("라이브채널 대표명")
  @Column(name = "live_channel_title")
  private String liveChannelTitle;


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
  @Column(name = "mod_date")
  @ApiModelProperty("변경 날짜")
  private LocalDateTime modDate;

  /**
   * 변경 id
   */
  @Column(name = "mod_id")
  @ApiModelProperty("변경 id")
  private String modId;

}

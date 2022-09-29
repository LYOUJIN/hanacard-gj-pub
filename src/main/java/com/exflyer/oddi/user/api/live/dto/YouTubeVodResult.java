package com.exflyer.oddi.user.api.live.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YouTubeVodResult {

  @ApiModelProperty(value = "채널타입코드", position = 1)
  private String channelType;

  @ApiModelProperty(value = "유튜브 동영상id", position = 2)
  private String youtubeId;

  @ApiModelProperty(value = "파트너 순번", position = 3)
  private Long partnerSeq;

  @ApiModelProperty(value = "유튜브 동영상 등록일", position = 4)
  private String youtubeRegDate;

  @ApiModelProperty(value = "유튜브 제목", position = 5)
  private String youtubeTitle;

  @ApiModelProperty(value = "유튜브 설명", position = 6)
  private String youtubeDescription;

  @ApiModelProperty(value = "유튜브 썸네일 정보", position = 7)
  private String youtubeThumbnails;

  @ApiModelProperty(value = "유튜브 재생ID", position = 8)
  private String youtubePlayId;

  @ApiModelProperty(value = "생성 날짜", position = 9)
  private String regDate;

  @ApiModelProperty(value = "유튜브 플레이 경로", position = 10)
  private String youtubeUrl;

  @ApiModelProperty(value = "노출여부", position = 11)
  private Boolean expo;

}

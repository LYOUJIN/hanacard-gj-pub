package com.exflyer.oddi.user.api.adv.subway.dto;

import com.exflyer.oddi.user.models.Youtube;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubwayPartnerListRes {

  @ApiModelProperty(value="순번", position = 0)
  private long seq;

  @ApiModelProperty(value="묶음순번", position = 0)
  private long productSeq;

  @ApiModelProperty(value="매장이름", position = 0)
  private String mallName;

  @ApiModelProperty(value="요약", position = 0)
  private String summary;

  @ApiModelProperty(value="설명", position = 0)
  private String description;

  @ApiModelProperty(value="배지 코드", position = 0)
  private String badgeCode;

  @ApiModelProperty(value="운영 광고 슬롯", position = 0)
  private long totalSlot;

  @ApiModelProperty(value="슬롯당 노출 시간", position = 0)
  private long slotVideoTime;

  @ApiModelProperty(value="슬롯당 금액", position = 0)
  private long slotPrice;

  @ApiModelProperty(value="운영 시작 시간", position = 0)
  private String operationStartTime;

  @ApiModelProperty(value="운영 종료 시간", position = 0)
  private String operationEndTime;

  @ApiModelProperty(value="광고화면", position = 0)
  private String display;

  @ApiModelProperty(value="도면 파일경로", position = 0)
  private String mapFilePath;

  @ApiModelProperty(value="지하철명", position = 0)
  private List<SubwayLine> subwayLineList;

  @ApiModelProperty(value="이미지리스트", position = 0)
  private List<SubwayImage> subwayImageList;

  @ApiModelProperty(value = "광고사례 조회(파트너에게 매핑된 가장최근 VOD조회)", position = 0)
  private Youtube youtube;

}

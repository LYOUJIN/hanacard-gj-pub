package com.exflyer.oddi.user.api.live.dto;

import com.exflyer.oddi.user.share.dto.PagingSearch;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchVodReq extends PagingSearch {

  @ApiModelProperty(value = "채널타입코드", position = 1)
  private String channelType;

  @ApiModelProperty(value = "최신순", position = 2)
  private Boolean isNewOrder;

  @ApiModelProperty(value = "채널타입코드", position = 3)
  private Boolean isOldOrder;

}

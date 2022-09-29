package com.exflyer.oddi.user.share.dto;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingResult<T> {

  @ApiModelProperty(value = "총 카운트", position = 0)
  private long total;

  @ApiModelProperty(value = "총 페이지", position = 1)
  private int pages;

  @ApiModelProperty(value = "현재 페이지", position = 2)
  private int pageNum;

  @ApiModelProperty(value = "데이터", position = 3)
  private List<T> data;

  public static PagingResult createResultDto(Page queryResult) {
    return queryResult == null
        ? new PagingResult()
        : new PagingResult(
            queryResult.getTotal(),
            queryResult.getPages(),
            queryResult.getPageNum(),
            queryResult.getResult());
  }
}

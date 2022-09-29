package com.exflyer.oddi.user.api.voc.notice;

import com.exflyer.oddi.user.api.voc.notice.dto.NoticeCondition;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeConditionRes;
import com.exflyer.oddi.user.api.voc.notice.dto.NoticeDetailRes;
import com.exflyer.oddi.user.api.voc.notice.service.NoticeService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "공지사항", protocols = "http")
@Slf4j
@RestController
public class NoticeApi {

  @Autowired
  private NoticeService noticeService;

  @ApiOperation(value = "공지사항 목록 조회 ", notes = "공지사항 목록 조회 API 입니다.")
  @GetMapping(path = "/notice")
  public ApiResponseDto<PagingResult<NoticeConditionRes>> findNotice(NoticeCondition noticeCondition) {
    PagingResult noticeConditionResList = noticeService.findByCondition(noticeCondition);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, noticeConditionResList);
  }

  @ApiOperation(value = "공지사항 상세 조회 ", notes = "공지사항 상세 조회 API 입니다.")
  @GetMapping(path = "/notice/{rownum}")
  public ApiResponseDto<NoticeDetailRes> findNoticeDetail(@PathVariable Long rownum) throws ApiException {
    NoticeDetailRes noticeDetailRes = noticeService.findNoticeDetail(rownum);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, noticeDetailRes);
  }

}

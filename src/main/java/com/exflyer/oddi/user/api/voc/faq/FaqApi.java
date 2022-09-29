package com.exflyer.oddi.user.api.voc.faq;


import com.exflyer.oddi.user.api.voc.faq.dto.FaqCondition;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqConditionResult;
import com.exflyer.oddi.user.api.voc.faq.service.FaqService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "FAQ", protocols = "http")
@Slf4j
@RestController
public class FaqApi {

  @Autowired
  private FaqService faqService;

  @ApiOperation(value = "FAQ 분류 코드 조회 ", notes = "FAQ 분류 코드 조회")
  @GetMapping(path = "/faq/type")
  public ApiResponseDto<List<FaqConditionResult>> findFaqCode() {
    List<Code> faqTypeOptional = faqService.findFaqCode();
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, faqTypeOptional);
  }

  @ApiOperation(value = "FAQ 목록 조회 ", notes = "FAQ 목록 조회 API 입니다.")
  @GetMapping(path = "/faq")
  public ApiResponseDto<List<FaqConditionResult>> findFaq(FaqCondition condition) {
    List<FaqConditionResult> conditionResult = faqService.findByCondition(condition);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, conditionResult);
  }

  @ApiOperation(value = "FAQ 조회수 증가 ", notes = "FAQ 조회수 증가 API 입니다.")
  @GetMapping(path = "/faq/{seq}")
  public ApiResponseDto<List<FaqConditionResult>> updateClickCount(@PathVariable Long seq) {
    faqService.updateClickCount(seq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

}

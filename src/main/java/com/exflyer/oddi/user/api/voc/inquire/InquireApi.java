package com.exflyer.oddi.user.api.voc.inquire;


import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.annotaions.OddiEncrypt;
import com.exflyer.oddi.user.api.adv.partner.dto.PartnerJoinReq;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqCondition;
import com.exflyer.oddi.user.api.voc.faq.dto.FaqConditionResult;
import com.exflyer.oddi.user.api.voc.faq.service.FaqService;
import com.exflyer.oddi.user.api.voc.inquire.dto.*;
import com.exflyer.oddi.user.api.voc.inquire.service.InquireService;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import com.exflyer.oddi.user.share.dto.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "1:1문의", protocols = "http")
@Slf4j
@RestController
public class InquireApi {

  @Autowired
  private InquireService inquireService;

  @ApiOperation(value = "1:1문의 문의유형코드", notes = "1:1문의유형코드 조회 입니다.")
  @GetMapping(path = "/inquire/codes")
  public ApiResponseDto<List<Code>> getCodes() {
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, inquireService.getTypeCode());
  }

  @ApiOperation(value = "1:1문의 등록(비회원)", notes = "1:1문의 비회원 등록")
  @PostMapping("/inquire/nomember")
  @OddiEncrypt
  public ApiResponseDto save(@Validated @RequestBody InquireNoMemberReq req)throws ApiException {
    inquireService.saveNoMemberReq(req);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "1:1문의 수정(회원)", notes = "1:1문의 회원 수정 api입니다.")
  @PutMapping("/inquire/member/{seq}")
  @OddiEncrypt
  @LoginNeedApi
  public ApiResponseDto save(@Validated @RequestBody InquireMemberReq req, @PathVariable Long seq, MemberAuth memberAuth)throws ApiException {
    req.setSeq(seq);
    req.setMemberId(memberAuth.getId());
    inquireService.saveMemberReq(req);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "1:1문의 회원 등록", notes = "1:1문의 회원 등록")
  @PostMapping("/inquire/member")
  @OddiEncrypt
  @LoginNeedApi
  public ApiResponseDto memberSave(@Validated @RequestBody InquireMemberReq req, MemberAuth memberAuth)throws ApiException {
    req.setMemberId(memberAuth.getId());
    inquireService.saveMemberReq(req);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "1:1문의 목록조회", notes = "1:1문의 목록조회")
  @LoginNeedApi
  @GetMapping("/inquire/member")
    public ApiResponseDto<PagingResult<InquireListRes>> findInquire(InquireListReq req, MemberAuth memberAuth)throws ApiException {
    req.setMemberId(memberAuth.getId());
    PagingResult res = inquireService.findByMemberId(req);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
  }

  @ApiOperation(value = "1:1문의 상세보기(로그인)", notes = "1:1문의 상세보기")
  @LoginNeedApi
  @GetMapping("/inquire/member/{seq}")
  public ApiResponseDto<InquireDetailRes> getDetail(@PathVariable Long seq, MemberAuth memberAuth)throws ApiException {
    InquireDetailRes res = inquireService.get(seq, memberAuth.getId(), null);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
  }

  @ApiOperation(value = "1:1문의 상세보기(비로그인)", notes = "1:1문의 상세보기(비로그인)")
  @GetMapping("/inquire/nomember/{uniq}")
  public ApiResponseDto<InquireDetailRes> getDetailUnauth(@PathVariable String uniq)throws ApiException {
    if(StringUtils.isNumeric(uniq)){
      throw new ApiException(ApiResponseCodes.BAD_REQUEST);
    }
    InquireDetailRes res = inquireService.get(0L, null, uniq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, res);
  }

}

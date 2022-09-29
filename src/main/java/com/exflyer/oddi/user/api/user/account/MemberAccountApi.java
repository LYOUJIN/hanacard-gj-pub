package com.exflyer.oddi.user.api.user.account;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.annotaions.OddiEncrypt;
import com.exflyer.oddi.user.api.user.account.dto.MemberAddReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberIdDuplicationReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccount;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccountModReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberPasswordReq;
import com.exflyer.oddi.user.api.user.account.dto.VerificationNumberReq;
import com.exflyer.oddi.user.api.user.account.service.MemberAccountService;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "사용자 계정", protocols = "http")
@Slf4j
@RestController
public class MemberAccountApi {

  @Autowired
  private MemberAccountService memberAccountService;


  @ApiOperation(value = "회원 가입 아이디 중복 체크", notes = "회원 가입 API")
  @PostMapping(path = "/account/duplication")
  @OddiEncrypt
  public ApiResponseDto validDuplication(@Validated @RequestBody MemberIdDuplicationReq memberIdDuplicationReq) {
    memberIdDuplicationReq.setProvider("oddi");
    boolean isDuplication = memberAccountService.isDuplicationId(memberIdDuplicationReq.getEmail(),memberIdDuplicationReq.getProvider());
    // 중복이 아닐 경우 SUCCESS 코드
    if (isDuplication) {
      return new ApiResponseDto(ApiResponseCodes.DUPLICATE);
    } else {
      return new ApiResponseDto(ApiResponseCodes.SUCCESS);
    }
  }

  @ApiOperation(value = "인증 번호 발송 ", notes = "전화 번호로 인증번호를 발송 하는 API 입니다.")
  @PostMapping(path = "/verification-number/{phoneNumber}")
  public ApiResponseDto sendVerificationNumber(@PathVariable String phoneNumber) throws IOException, ApiException {
    memberAccountService.sendVerificationNumber(phoneNumber);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "인증 번호 확인", notes = "발송된 인증 번호를 확인 하는 API 입니다.")
  @PostMapping(path = "/verification-number/check")
  @OddiEncrypt
  public ApiResponseDto validVerificationNumber(@Validated @RequestBody VerificationNumberReq verificationNumberReq)
    throws ApiException {
    boolean validResult = memberAccountService.validVerificationNumber(verificationNumberReq);
    return validResult ? new ApiResponseDto(ApiResponseCodes.SUCCESS) : new ApiResponseDto(ApiResponseCodes.DUPLICATE);
  }

  @ApiOperation(value = "회원 가입", notes = "회원 가입 API")
  @PostMapping(path = "/account")
  @OddiEncrypt
  public ApiResponseDto addMember(@Validated @RequestBody MemberAddReq memberAddReq) throws ApiException {
    memberAddReq.setProvider("oddi");
    memberAccountService.addMember(memberAddReq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, null);
  }

  @ApiOperation(value = "사용자 내 정보 조회 ", notes = "사용자 내 정보 조회 API 입니다.")
  @GetMapping(path = "/account/my")
  @OddiEncrypt
  @LoginNeedApi
  public ApiResponseDto<MemberMyAccount> findMyInfo(MemberAuth memberAuth) throws IOException, ApiException {
    MemberMyAccount memberMyAccount = memberAccountService.findMyInfo(memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, memberMyAccount);
  }

  @ApiOperation(value = "사용자 내 정보 수정 ", notes = "사용자 내 정보 수정 API 입니다.")
  @PutMapping(path = "/account/my")
  @OddiEncrypt
  @LoginNeedApi
  public ApiResponseDto<MemberMyAccount> modifyMyAccount(@Validated @RequestBody MemberMyAccountModReq memberMyAccountModReq, MemberAuth memberAuth) throws IOException, ApiException {
    memberMyAccountModReq.setId(memberAuth.getId());
    memberAccountService.modifyMyAccount(memberMyAccountModReq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "사용자 비밀번호 변경 ", notes = "사용자 비밀번호 변경 API 입니다.")
  @PostMapping(path = "/account/my/passwordChange")
  @LoginNeedApi
  public ApiResponseDto<MemberMyAccount> modifyMyPasswordChange(@Validated @RequestBody MemberPasswordReq memberPasswordReq, MemberAuth memberAuth) throws IOException, ApiException {
    memberPasswordReq.setId(memberAuth.getId());
    memberAccountService.modifyMyPasswordChange(memberPasswordReq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }


  @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴 API 입니다.")
  @PostMapping(path = "/account/my/resign")
  @LoginNeedApi
  public ApiResponseDto<MemberMyAccount> modifyMyResign(MemberAuth memberAuth) throws ApiException {
    memberAccountService.modifyMyResign(memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }


}

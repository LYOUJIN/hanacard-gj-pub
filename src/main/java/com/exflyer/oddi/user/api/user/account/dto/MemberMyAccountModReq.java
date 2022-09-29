package com.exflyer.oddi.user.api.user.account.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.annotaions.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberMyAccountModReq {

  @ApiModelProperty(hidden = true)
  private String id;

  @NotBlank
  @ApiModelProperty(value = "이름", position = 2)
  private String name;

  @NotBlank
  @ApiModelProperty(value = "전화번호", position = 2)
  @EncryptField
  @PhoneNumber
  private String phoneNumber;

  @NotBlank
  @ApiModelProperty(value = "이메일", position = 10)
  @EncryptField
  @Email
  private String email;

  @ApiModelProperty(value = "개인 사업자 상호명", position = 4)
  private String pCompanyName;

  @ApiModelProperty(value = "개인 사업자 대표명", position = 5)
  private String pCeo;

  @ApiModelProperty(value = "개인 사업자 사업자번호", position = 6)
  private String pBusinessLicenseNumber;

  @ApiModelProperty(value = "개인 사업자 등록증 순번", position = 6)
  private Long pBusinessLicenseFileSeq;

  @ApiModelProperty(value = "법인 사업자 상호명", position = 7)
  private String cCompanyName;

  @ApiModelProperty(value = "법인 사업자 대표명", position = 8)
  private String cCeo;

  @ApiModelProperty(value = "법인 사업자 사업자번호", position = 9)
  private String cBusinessLicenseNumber;

  @ApiModelProperty(value = "법인 사업자 등록증 파일 순번", position = 6)
  private Long cBusinessLicenseFileSeq;

  @ApiModelProperty(value = "광고 수신 동의 여부", position = 10)
  private boolean receiveConsent;

  @ApiModelProperty(value = "광고 이메일수신 동의여부", position = 11)
  private boolean emailReceiveConsent;

}

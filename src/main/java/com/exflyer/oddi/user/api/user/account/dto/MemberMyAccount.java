package com.exflyer.oddi.user.api.user.account.dto;

import com.exflyer.oddi.user.annotaions.DecryptField;
import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.MemberCompany;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberMyAccount {

  @ApiModelProperty(value = "이름", position = 1)
  private String name;

  @ApiModelProperty(value = "이메일", position = 2)
  @DecryptField
  private String email;

  @ApiModelProperty(value = "전화번호", position = 3)
  @DecryptField
  private String phoneNumber;

  @ApiModelProperty(value = "개인 사업자 상호명", position = 4)
  private String pCompanyName;

  @ApiModelProperty(value = "개인 사업자 대표명", position = 5)
  private String pCeo;

  @ApiModelProperty(value = "개인 사업자 사업자번호", position = 6)
  private String pBusinessLicenseNumber;

  @ApiModelProperty(value = "개인 사업자 등록증 파일 순번", position = 6)
  private Long pBusinessLicenseFileSeq;

  @ApiModelProperty(value = "개인 사업자 등록증 파일명", position = 6)
  private String pBusinessLicenseFileName;

  @ApiModelProperty(value = "개인 사업자 등록증 파일 경로", position = 6)
  private String pBusinessLicenseFilePath;


  @ApiModelProperty(value = "법인 사업자 상호명", position = 7)
  private String cCompanyName;

  @ApiModelProperty(value = "법인 사업자 대표명", position = 8)
  private String cCeo;

  @ApiModelProperty(value = "법인 사업자 사업자번호", position = 9)
  private String cBusinessLicenseNumber;

  @ApiModelProperty(value = "법인 사업자 등록증 파일 순번", position = 6)
  private Long cBusinessLicenseFileSeq;

  @ApiModelProperty(value = "법인 사업자 등록증 파일 이름", position = 6)
  private String cBusinessLicenseFileName;

  @ApiModelProperty(value = "법인 사업자 등록증 파일 경로", position = 6)
  private String cBusinessLicenseFilePath;

  @ApiModelProperty(value = "수신 동의", position = 6)
  private boolean receiveConsent = false;

  @ApiModelProperty(value = "광고 이메일수신 동의여부", position = 6)
  private boolean emailReceiveConsent;

  public MemberMyAccount(Member member) {
    this.name = member.getName();
    this.phoneNumber = member.getPhoneNumber();
    this.email = member.getEmail();
    this.receiveConsent = member.isReceiveConsent();
    this.emailReceiveConsent = member.isEmailReceiveConsent();
  }

  public void setCompanyInfo(MemberCompany memberCompany, Files files) {
    if (memberCompany.isCorporation()) {
      this.cCompanyName = memberCompany.getName();
      this.cCeo = memberCompany.getCeo();
      this.cBusinessLicenseNumber = memberCompany.getBusinessLicenseNumber();
      if (files != null) {
        this.cBusinessLicenseFileSeq = files.getSeq();
        this.cBusinessLicenseFileName = files.getName();
        this.cBusinessLicenseFilePath = files.getPath();
      }
    } else {
      this.pCompanyName = memberCompany.getName();
      this.pCeo = memberCompany.getCeo();
      this.pBusinessLicenseNumber = memberCompany.getBusinessLicenseNumber();
      if (files != null) {
        this.pBusinessLicenseFileSeq = files.getSeq();
        this.pBusinessLicenseFileName = files.getName();
        this.pBusinessLicenseFilePath = files.getPath();
      }

    }

  }
}

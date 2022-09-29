package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.adv.adv.dto.AddMemeberReq;
import com.exflyer.oddi.user.api.adv.adv.dto.AdvAddReq;
import com.exflyer.oddi.user.api.user.account.dto.MemberMyAccountModReq;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 회원 회사
 */
@Data
@Entity
@ApiModel("회원 회사")
@Table(name = "member_company")
@NoArgsConstructor
public class MemberCompany implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 순번
   */
  @Id
  @ApiModelProperty("순번")
  @Column(name = "seq", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  /**
   * 회원 id
   */
  @ApiModelProperty("회원 id")
  @Column(name = "member_id", nullable = false)
  private String memberId;

  /**
   * 법인 여부
   */
  @ApiModelProperty("법인 여부")
  @Column(name = "corporation", nullable = false)
  private boolean corporation;

  /**
   * 이름
   */
  @ApiModelProperty("회사명")
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * 대표
   */
  @ApiModelProperty("대표")
  @Column(name = "ceo", nullable = false)
  private String ceo;

  /**
   * 사업자 등록증 번호
   */
  @ApiModelProperty("사업자 등록증 번호")
  @Column(name = "business_license_number", nullable = false)
  private String businessLicenseNumber;

  /**
   * 사업자 등록증 파일
   */
  @ApiModelProperty("사업자 등록증 파일")
  @Column(name = "business_license_file", nullable = false)
  private Long businessLicenseFile;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  /**
   * 변경 날짜
   */
  @Column(name = "mod_date")
  @ApiModelProperty("변경 날짜")
  private LocalDateTime modDate;
  public void setInfoByMyAccountModReq(MemberMyAccountModReq memberMyAccountModReq) {
    if (this.isCorporation()) {
      this.name = memberMyAccountModReq.getCCompanyName();
      this.businessLicenseFile = memberMyAccountModReq.getCBusinessLicenseFileSeq();
      this.ceo = memberMyAccountModReq.getCCeo();
      this.businessLicenseNumber = memberMyAccountModReq.getCBusinessLicenseNumber();
    } else{
      this.name = memberMyAccountModReq.getPCompanyName();
      this.businessLicenseFile = memberMyAccountModReq.getPBusinessLicenseFileSeq();
      this.ceo = memberMyAccountModReq.getPCeo();
      this.businessLicenseNumber = memberMyAccountModReq.getPBusinessLicenseNumber();
    }
    this.modDate = LocalDateUtils.krNow();
  }

  public MemberCompany(AddMemeberReq addMemeberReq, String regId, String code) {
    this.memberId = regId;
    this.name = addMemeberReq.getCompanyName();
    this.ceo = addMemeberReq.getCeo();
    this.businessLicenseNumber = addMemeberReq.getBusinessLicenseNumber();
    this.businessLicenseFile = addMemeberReq.getBusinessLicenseFile();
    this.regDate = LocalDateUtils.krNow();

    //법인사업자
    if(!"BCT002".equals(code)) {
      this.corporation = true;
    }

    if(addMemeberReq.getSeq() != null) {
      this.seq = addMemeberReq.getSeq();
    }
  }

}

package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고객 약관 동의
 */
@Data
@Entity
@ApiModel("고객 약관 동의")
@Table(name = "member_terms")
@NoArgsConstructor
public class MemberTerms implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private MemberTermsPk memberTermsPk;

  /**
   * 생성 날짜
   */
  @ApiModelProperty("생성 날짜")
  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  /**
   * 약관동의여부
   */
  @ApiModelProperty("약관동의여부")
  @Column(name = "terms_agree")
  private Boolean termsAgree;


  public MemberTerms(String id, Long termSeq, Boolean termsAgree) {
    this.memberTermsPk = new MemberTermsPk(id, termSeq);
    this.regDate = LocalDateUtils.krNow();
    this.termsAgree = termsAgree;
  }
}

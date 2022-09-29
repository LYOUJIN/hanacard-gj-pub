package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고객 약관 동의
 */
@Data
@Embeddable
@NoArgsConstructor
public class MemberTermsPk implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 회원 id
   */
  @ApiModelProperty("회원 id")
  @Column(name = "member_id", nullable = false)
  private String memberId;

  /**
   * 약관 그룹 순번
   */
  @ApiModelProperty("약관 그룹 순번")
  @Column(name = "terms_seq", nullable = false)
  private Long termsSeq;

  public MemberTermsPk(String memberId, Long termSeq) {
    this.memberId = memberId;
    this.termsSeq = termSeq;
  }
}

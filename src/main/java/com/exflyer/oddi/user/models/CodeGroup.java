package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 코드 그룹
 */
@Data
@Entity
@ApiModel("코드 그룹")
@Table(name = "code_group")
public class CodeGroup implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 코드
   */
  @Id
  @ApiModelProperty("코드")
  @Column(name = "code", nullable = false)
  private String code;

  /**
   * 이름
   */
  @Column(name = "name")
  @ApiModelProperty("이름")
  private String name;

  /**
   * 사용
   */
  @Column(name = "usable")
  @ApiModelProperty("사용")
  private Boolean usable;

  /**
   * 정렬 번호
   */
  @Column(name = "ordering")
  @ApiModelProperty("정렬 번호")
  private Integer ordering;

}

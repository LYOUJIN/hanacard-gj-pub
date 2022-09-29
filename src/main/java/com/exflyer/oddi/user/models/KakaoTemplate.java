package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 카카오톡 알림톡 템플릿
 */
@Data
@Entity
@ApiModel("카카오톡 알림톡 템플릿")
@Table(name = "kakao_template")
public class KakaoTemplate {

  /**
   * 템플릿 코드
   */
  @Id
  @ApiModelProperty("템플릿 코드")
  @Column(name = "template_id", nullable = false)
  private String templateId;


  /**
   * 템플릿 제목
   */
  @ApiModelProperty("템플릿 제목")
  @Column(name = "template_name", nullable = false)
  private String templateName;

  /**
   * 템플릿 내용
   */
  @ApiModelProperty("템플릿 내용")
  @Column(name = "template_contents", nullable = false)
  private String templateContents;


}

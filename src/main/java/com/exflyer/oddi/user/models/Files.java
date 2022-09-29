package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.files.dto.AwsS3MetaData;
import com.exflyer.oddi.user.api.files.dto.AwsS3Result;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일
 */
@Data
@Entity
@ApiModel("파일")
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
public class Files implements Serializable {

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
   * s3_file_key
   */
  @ApiModelProperty("s3_file_key")
  @Column(name = "s3_file_key", nullable = false)
  private String s3FileKey;

  /**
   * s3_bucket
   */
  @ApiModelProperty("s3_bucket")
  @Column(name = "s3_bucket", nullable = false)
  private String s3Bucket;

  /**
   * 경로
   */
  @ApiModelProperty("경로")
  @Column(name = "path", nullable = false)
  private String path = "";

  /**
   * 수정 날짜
   */
  @ApiModelProperty("수정 날짜")
  @Column(name = "reg_date", nullable = false)
  private LocalDateTime regDate;

  /**
   * 이름
   */
  @Column(name = "name")
  @ApiModelProperty("이름")
  private String name = "";

  /**
   * 확장자
   */
  @ApiModelProperty("확장자")
  @Column(name = "extension", nullable = false)
  private String extension;

  /**
   * 종류(사업자 등록증 등)
   */
  @Column(name = "type")
  @ApiModelProperty("종류(S3, Local 등)")
  private String type;

  /**
   * 등록id
   */
  @Column(name = "reg_id")
  @ApiModelProperty("등록id")
  private String regId;

  @Column(name = "content_type")
  @ApiModelProperty("파일 컨텐츠 타입")
  private String contentType;

  public Files(AwsS3Result awsS3Result, AwsS3MetaData metadata, String regId) {
    this.s3FileKey = awsS3Result.getS3FileKey();
    this.s3Bucket = awsS3Result.getS3Bucket();
    this.name = metadata.getOriginName();
    this.extension = metadata.getExtension();
    this.type = metadata.getType();
    this.regId = regId;
    this.path = awsS3Result.getPath();
    this.regDate = LocalDateUtils.krNow();
    this.contentType = metadata.getContentType();

  }
}

package com.exflyer.oddi.user.api.files.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileUploadResult {

  private Long fileSeq;

  private String url;

  private String originName;

  private String uniqFileName;

  public FileUploadResult(AwsS3Result awsS3Result, AwsS3MetaData metadata, Long seq) {
    this.fileSeq = seq;
    this.url = awsS3Result.getPath();
    this.originName = metadata.getOriginName();
    this.uniqFileName = awsS3Result.getS3FileKey();
  }
}

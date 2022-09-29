package com.exflyer.oddi.user.api.files.dto;

import lombok.Data;

@Data
public class AwsS3Result {
  private String path;

  private String s3FileKey;

  private String s3Bucket;

}

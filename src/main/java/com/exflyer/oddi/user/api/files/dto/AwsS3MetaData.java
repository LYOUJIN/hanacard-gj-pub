package com.exflyer.oddi.user.api.files.dto;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.Data;

@Data
public class AwsS3MetaData extends ObjectMetadata {

  private String type = "S3";

  private String dir;

  private String originName;

  private String extension;

}

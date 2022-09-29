package com.exflyer.oddi.user.api.files.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AwsS3PublicConfig {

  @Value("${aws.s3.public.bucket}")
  private String bucket;

  @Value("${aws.s3.public.video-dir}")
  private String videoDir;

  @Value("${aws.s3.public.attachments-dir}")
  private String attachments;

  @Value("${aws.s3.public.adv_image-dir}")
  private String advImageDir;

  @Value("${aws.s3.public.private-dir}")
  private String privateDir;

  @Value("${aws.s3.public.corporation-dir}")
  private String corporationDir;

  @Value("${aws.s3.public.url-host}")
  private String urlHost;

  @Value("${aws.s3.public.region}")
  private String region;

}

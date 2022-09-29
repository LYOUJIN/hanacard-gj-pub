package com.exflyer.oddi.user.api.files.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.exflyer.oddi.user.api.files.config.AwsS3PublicConfig;
import com.exflyer.oddi.user.api.files.dto.AwsS3MetaData;
import com.exflyer.oddi.user.api.files.dto.AwsS3Result;
import com.exflyer.oddi.user.api.files.dto.FileDownloadInfo;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Files;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class AwsS3Service {

  private AmazonS3 s3PublicClient;

  @Autowired
  private AwsS3PublicConfig awsS3PublicConfig;


  @PostConstruct
  public void setS3PublicClient() {
    s3PublicClient = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.AP_NORTHEAST_2)
      .build();
  }

  public AwsS3Result uploadFile(MultipartFile multipartFile, String uniqFileName, AwsS3MetaData metadata)
    throws ApiException {
    try {

      String dir = metadata.getDir();
      if ("attachments".equals(dir)) {
        metadata.setDir(awsS3PublicConfig.getAttachments());
      } else if ("private".equals(dir)) {
        metadata.setDir(awsS3PublicConfig.getPrivateDir());
      } else if ("corporation".equals(dir)) {
        metadata.setDir(awsS3PublicConfig.getCorporationDir());
      } else if ("video".equals(dir)) {
        metadata.setDir(awsS3PublicConfig.getVideoDir());
      } else if ("advSingle".equals(dir)) {
        metadata.setDir(awsS3PublicConfig.getAdvImageDir());
      }

      s3PublicClient
        .putObject(
          new PutObjectRequest(awsS3PublicConfig.getBucket() + metadata.getDir(), uniqFileName,
            multipartFile.getInputStream(),
            metadata));
      ;
      AwsS3Result awsS3Result = new AwsS3Result();
      awsS3Result.setPath(awsS3PublicConfig.getUrlHost() + metadata.getDir() + "/" + uniqFileName);
      awsS3Result.setS3Bucket(awsS3PublicConfig.getBucket() + metadata.getDir());
      awsS3Result.setS3FileKey(uniqFileName);
      return awsS3Result;
    } catch (Exception e) {
      log.error("aws s3 exception", e);
      throw new ApiException(ApiResponseCodes.AWS_S3_FAIL);
    }
  }

  public void delete(List<Files> fileList) {
    for (Files files : fileList) {
      s3PublicClient.deleteObject(files.getS3Bucket(), files.getS3FileKey());
    }
  }

  public FileDownloadInfo downloadFileByFileSeq(Files files) throws ApiException {

    FileDownloadInfo downloadInfo = new FileDownloadInfo();
    downloadInfo.setName(files.getName());

    try {

      downloadInfo.setContents(IOUtils
        .toByteArray(
          s3PublicClient.getObject(files.getS3Bucket(), files.getS3FileKey()).getObjectContent()));
    } catch (Exception e) {
      log.error("aws s3 exception", e);
      throw new ApiException(ApiResponseCodes.AWS_S3_FAIL);
    }
    return downloadInfo;
  }

  public AwsS3Result uploadMustadFile(String mustadUrl, String uniqFileName, AwsS3MetaData metadata)  throws ApiException {
    try {
      for (int i = 0; i < 2; i++) {
        try {
          uploadMustadFileFromAws(metadata, new BufferedInputStream(new URL(mustadUrl).openStream()), uniqFileName);

          break;
        } catch (Exception e) {
          if (1 == i) {
            throw e;
          } else {
            log.info("retry mustad image conversion");
            Thread.sleep(200);
          }
        }
      }

      AwsS3Result awsS3Result = new AwsS3Result();
      awsS3Result.setPath(awsS3PublicConfig.getUrlHost() + metadata.getDir() + "/" + uniqFileName);
      awsS3Result.setS3Bucket(awsS3PublicConfig.getBucket() + metadata.getDir());
      awsS3Result.setS3FileKey(uniqFileName);

      return awsS3Result;
    } catch (Exception e) {
      log.error("aws s3 exception", e);
      throw new ApiException(ApiResponseCodes.AWS_S3_FAIL);
    }
  }

  public AwsS3Result uploadMustadRotateFile(String mustadUrl, String uniqFileName, AwsS3MetaData metadata, String orgFileName)  throws ApiException {
    try {
      URL imageURL = new URL(mustadUrl);
      BufferedImage inputImage;

      for (int i = 0; i < 2; i++) {
        try {
          //portrait_right, portrait_left, portrait
          if(orgFileName.indexOf("portrait_left") > -1) {
            inputImage = rotateImage(ImageIO.read(imageURL), 90);
          }else {
            inputImage = rotateImage(ImageIO.read(imageURL), 270);
          }

          ByteArrayOutputStream os = new ByteArrayOutputStream();
          ImageIO.write(inputImage, "jpg", os);

          uploadMustadFileFromAws(metadata, new ByteArrayInputStream(os.toByteArray()), uniqFileName);

          break;
        } catch (Exception e) {
          if (1 == i) {
            throw e;
          } else {
            log.info("retry mustad image conversion");
            Thread.sleep(200);
          }
        }
      }

      AwsS3Result awsS3Result = new AwsS3Result();
      awsS3Result.setPath(awsS3PublicConfig.getUrlHost() + metadata.getDir() + "/" + uniqFileName);
      awsS3Result.setS3Bucket(awsS3PublicConfig.getBucket() + metadata.getDir());
      awsS3Result.setS3FileKey(uniqFileName);

      return awsS3Result;
    } catch (Exception e) {
      log.error("aws s3 exception", e);
      throw new ApiException(ApiResponseCodes.AWS_S3_FAIL);
    }
  }

  public void uploadMustadFileFromAws(AwsS3MetaData metadata, InputStream is, String fileName) throws InterruptedException {
    metadata.setDir(awsS3PublicConfig.getAdvImageDir());
    String bucketName = StringUtils.join(awsS3PublicConfig.getBucket(), metadata.getDir());

    for (int i = 0; i < 2; i++) {
      try {
        s3PublicClient.putObject(new PutObjectRequest(bucketName, fileName, is, metadata));

        break;
      } catch (Exception e) {
        if (1 == i) {
          throw e;
        } else {
          log.info("retry due to s3 error");
          Thread.sleep(200);
        }
      }
    }
  }

  public BufferedImage rotateImage(BufferedImage imageToRotate, int radians) {
    int widthOfImage = imageToRotate.getWidth();
    int heightOfImage = imageToRotate.getHeight();
    int typeOfImage = imageToRotate.getType();

    BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

    Graphics2D graphics2D = newImageFromBuffer.createGraphics();

    graphics2D.rotate(Math.toRadians(radians), widthOfImage / 2, heightOfImage / 2);
    graphics2D.drawImage(imageToRotate, null, 0, 0);

    return newImageFromBuffer;
  }
}

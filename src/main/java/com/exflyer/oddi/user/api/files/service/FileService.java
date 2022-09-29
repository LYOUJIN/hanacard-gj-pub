package com.exflyer.oddi.user.api.files.service;

import com.exflyer.oddi.user.api.files.dto.AwsS3MetaData;
import com.exflyer.oddi.user.api.files.dto.AwsS3Result;
import com.exflyer.oddi.user.api.files.dto.FileDownloadInfo;
import com.exflyer.oddi.user.api.files.dto.FileUploadResult;
import com.exflyer.oddi.user.api.files.dto.MustadImgReq;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.repository.FilesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class FileService {

  @Autowired
  private AwsS3Service awsS3Service;

  @Autowired
  private FilesRepository filesRepository;

  public void updateMappingDone(List<Long> fileSeqList) {
    filesRepository.updateUseAble(fileSeqList);
  }

  public FileUploadResult uploadAttachments(MultipartFile file, String dir, String regId) throws ApiException {
    return uploadBusinessLicense(file, dir, regId);
  }

  public FileUploadResult uploadFile(MultipartFile file, String dir, String regId) throws ApiException {
    return uploadBusinessLicense(file, dir, regId);
  }

  private FileUploadResult uploadBusinessLicense(MultipartFile file, String dir, String regId) throws ApiException {
    String originalFileName = file.getOriginalFilename();
    String extension = FilenameUtils.getExtension(originalFileName);

    if(!"video".equals(dir)) {
      if (!validBusinessLicenseByExtension(extension)) {
        throw new ApiException(ApiResponseCodes.INVALID_EXTENSION);
      }
    }

    String uniqFileName = createUniqFileName(extension);
    AwsS3MetaData metadata = new AwsS3MetaData();
    metadata.setContentType(file.getContentType());
    metadata.setDir(dir);
    metadata.setOriginName(originalFileName);
    metadata.setExtension(extension);
    AwsS3Result awsS3Result = awsS3Service.uploadFile(file, uniqFileName, metadata);
    Files files = new Files(awsS3Result, metadata, regId);
    filesRepository.save(files);
    return new FileUploadResult(awsS3Result, metadata, files.getSeq());
  }

  private String createUniqFileName(String extension) {
    return UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
  }

  private Boolean validBusinessLicenseByExtension(String extension) {
    String extensionLower = extension.toLowerCase();
    return isImage(extensionLower) || isPdf(extensionLower);
  }

  private boolean isImage(String extension){
    String extensionLower = extension.toLowerCase();
    return StringUtils.contains(extensionLower, "gif")
      || StringUtils.contains(extensionLower, "jpg")
      || StringUtils.contains(extensionLower, "jpeg")
      || StringUtils.contains(extensionLower, "bmp")
      || StringUtils.contains(extensionLower, "svg")
      || StringUtils.contains(extensionLower, "png");
  }

  private boolean isPdf(String extension) {
    String extensionLower = extension.toLowerCase();
    return StringUtils.contains(extensionLower, "pdf")
        || StringUtils.contains(extensionLower, "doc")
        || StringUtils.contains(extensionLower, "docx")
        || StringUtils.contains(extensionLower, "xls")
        || StringUtils.contains(extensionLower, "xlsx")
        || StringUtils.contains(extensionLower, "ppt")
        || StringUtils.contains(extensionLower, "pptx")
        || StringUtils.contains(extensionLower, "hwp")
        || StringUtils.contains(extensionLower, "zip");
  }

  public String getFilePath(Long fileSeq) {
    Optional<Files> filesOptional = filesRepository.findById(fileSeq);
    Files files = filesOptional.orElseGet(Files::new);
    return StringUtils.defaultString(files.getPath(), "");
  }

    @Transactional
    public void delete(List<Long> fileSeqList) {

        List<Files> fileList = filesRepository.findAllById(fileSeqList);
        awsS3Service.delete(fileList);
        filesRepository.deleteAll(fileList);
    }

    public FileDownloadInfo fileDownload(Long fileSeq)  throws ApiException {
        Optional<Files> filesOptional = filesRepository.findById(fileSeq);
        Files files = filesOptional.orElseGet(Files::new);

        FileDownloadInfo fileDownloadInfo =  awsS3Service.downloadFileByFileSeq(files);
        return fileDownloadInfo;
    }

    public Files fileInfo(Long fileSeq) {
        Optional<Files> filesOptional = filesRepository.findById(fileSeq);
        Files files = filesOptional.orElseGet(Files::new);
        return files;
    }

  public FileUploadResult uploadMustadImg(MustadImgReq req) throws ApiException {
    return uploadMustadBusinessLicense(req);
  }


  private FileUploadResult uploadMustadBusinessLicense(MustadImgReq req) throws ApiException {

    //머스타드 이미지 확장자값이 없어서 임의로 지정함.
    String extension = "jpg";
    String uniqFileName = createUniqFileName(extension);

    AwsS3MetaData metadata = new AwsS3MetaData();

    metadata.setContentType("image");
    metadata.setOriginName(req.getName() + "_mustad");
    metadata.setExtension(extension);

    AwsS3Result awsS3Result = new AwsS3Result();
    if(req.getName().indexOf("portrait_left") > -1 || (req.getName().indexOf("portrait_right") > -1)) {
        awsS3Result = awsS3Service.uploadMustadRotateFile(req.getMustadUrl(), uniqFileName,metadata, req.getName());
    }else {
      awsS3Result = awsS3Service.uploadMustadFile(req.getMustadUrl(), uniqFileName,metadata);
    }
    Files files = new Files(awsS3Result, metadata, req.getRegId());
    filesRepository.save(files);
    return new FileUploadResult(awsS3Result, metadata, files.getSeq());
  }

}

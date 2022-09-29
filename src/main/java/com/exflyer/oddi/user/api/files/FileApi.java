package com.exflyer.oddi.user.api.files;

import com.exflyer.oddi.user.annotaions.LoginNeedApi;
import com.exflyer.oddi.user.api.files.dto.FileDownloadInfo;
import com.exflyer.oddi.user.api.files.dto.FileUploadResult;
import com.exflyer.oddi.user.api.files.dto.MustadImgReq;
import com.exflyer.oddi.user.api.files.service.FileService;
import com.exflyer.oddi.user.api.mustad.dto.MustadResult;
import com.exflyer.oddi.user.api.user.auth.dto.MemberAuth;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.share.dto.ApiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "파일 관련", protocols = "http")
@Slf4j
@RestController
public class FileApi {

  @Autowired
  private FileService fileService;

  @ApiOperation(value = "개인 사업자 등록증 업로드", notes = "단일 파일 업로드 API 입니다.")
  @PostMapping(value = "/file/p-business-license")
  @LoginNeedApi
  public ApiResponseDto uploadPrivateBusinessLicense(@RequestPart MultipartFile file, MemberAuth memberAuth) throws ApiException {
    FileUploadResult uploadResult = fileService.uploadAttachments(file, "private", memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, uploadResult);
  }

  @ApiOperation(value = "법인 사업자 등록증 업로드", notes = "단일 파일 업로드 API 입니다.")
  @PostMapping(value = "/file/c-business-license")
  @LoginNeedApi
  public ApiResponseDto uploadCorporationBusinessLicense(@RequestPart MultipartFile file, MemberAuth memberAuth) throws ApiException {
    FileUploadResult uploadResult = fileService.uploadAttachments(file, "corporation", memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, uploadResult);
  }

  @ApiOperation(value = "단일 파일 업로드 API", notes = "단일 파일 업로드 API 입니다.")
  @PostMapping(value = "/file/single")
  public ApiResponseDto upload(@RequestPart MultipartFile file, MemberAuth memberAuth) throws ApiException {
    FileUploadResult fileUploadResult = fileService.uploadAttachments(file, "attachments", memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, fileUploadResult);
  }

  @ApiOperation(value = "광고 파일 업로드 API", notes = "광고 파일 업로드 API 입니다.")
  @PostMapping(value = "/file/advSingle")
  @LoginNeedApi
  public ApiResponseDto uploadAdv(@RequestPart MultipartFile file, MemberAuth memberAuth) throws ApiException {
    FileUploadResult fileUploadResult = fileService.uploadAttachments(file, "advSingle", memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, fileUploadResult);
  }

  @ApiOperation(value = "광고 영상 업로드 API", notes = "광고 영상 파일 업로드 API 입니다.")
  @PostMapping(value = "/file/video")
  @LoginNeedApi
  public ApiResponseDto uploadVideo(@RequestPart MultipartFile file,MemberAuth memberAuth) throws ApiException {
    FileUploadResult fileUploadResult = fileService.uploadFile(file, "video", memberAuth.getId());
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, fileUploadResult);
  }

  @ApiOperation(value = "파일 삭제", notes = "파일 삭제 처리 API 입니다.")
  @DeleteMapping(value = "/file/{fileSeqList}")
  @LoginNeedApi
  public ApiResponseDto deleteFile(@PathVariable List<Long> fileSeqList) throws ApiException {
    fileService.delete(fileSeqList);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS);
  }

  @ApiOperation(value = "파일 다운", notes = "파일 다운 처리 API 입니다.")
  @GetMapping(value = "/file/{fileSeq}")
  @LoginNeedApi
  public ResponseEntity downloadFile(@PathVariable Long fileSeq) throws ApiException, IOException {
    FileDownloadInfo file = fileService.fileDownload(fileSeq);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    httpHeaders.setContentLength(file.getContents().length);
    httpHeaders.setContentDispositionFormData(
        "attachment", URLEncoder.encode(file.getName(), "UTF-8"));
    return new ResponseEntity<>(file.getContents(), httpHeaders, HttpStatus.OK);
  }

  @ApiOperation(value = "파일 정보조회", notes = "파일 정보조회 API 입니다.")
  @GetMapping(value = "/file/info/{fileSeq}")
  @LoginNeedApi
  public ApiResponseDto fileInfo(@PathVariable Long fileSeq)  {
    Files file = fileService.fileInfo(fileSeq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, file);
  }

  @ApiOperation(value = "머스타드 이미지 저장 API", notes = "머스타드 이미지 저장 API 입니다. ")
  @LoginNeedApi
  @PostMapping(path = "/file/mustad")
  public ApiResponseDto uploadMustadImg(@Validated @RequestBody MustadImgReq mustadImgReq, MemberAuth memberAuth) throws ApiException, Exception {
    mustadImgReq.setRegId(memberAuth.getId());
    FileUploadResult fileUploadResult = fileService.uploadMustadImg(mustadImgReq);
    return new ApiResponseDto(ApiResponseCodes.SUCCESS, fileUploadResult);
  }

}

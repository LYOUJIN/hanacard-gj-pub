package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberCompanyRes {

    @ApiModelProperty(value = "순번", position = 0)
    private Long memberCompany;

    @ApiModelProperty(value = "법인 여부", position = 1)
    private boolean corporation;

    @ApiModelProperty(value = "회사명", position = 2)
    private String memberCompanyName;

    @ApiModelProperty(value = "대표", position = 4)
    private String ceo;

    @ApiModelProperty(value = "사업자 등록증 파일", position = 4)
    private String businessLicenseFile;

    @ApiModelProperty(value = "사업자 등록증 번호", position = 4)
    private String businessLicenseNumber;

    @ApiModelProperty(value = "fileSeq", position = 4)
    private Long fileSeq;

    @ApiModelProperty(value = "파일명", position = 4)
    private String fileName;

    @ApiModelProperty(value = "확장자", position = 4)
    private String extension;

    @ApiModelProperty(value = "슬롯당 금액", position = 4)
    private String path;

    @ApiModelProperty(value = "파일 컨텐츠 타입", position = 4)
    private String contentType;

    @ApiModelProperty(value = "s3Bucket", position = 4)
    private String s3Bucket;

    @ApiModelProperty(value = "s3FileKey", position = 4)
    private String s3FileKey;

}
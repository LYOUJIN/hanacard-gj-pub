package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvFileRes {

    @ApiModelProperty(value = "파일번호", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "파일타입코드", position = 0)
    private String type;

    @ApiModelProperty(value = "파일타입명(이미지/동영상/머스타드)", position = 0)
    private String typeName;

    @ApiModelProperty(value = "노출순서", position = 0)
    private String viewOrder;

    @ApiModelProperty(value = "파일명", position = 0)
    private String name;

    @ApiModelProperty(value = "확장자", position = 0)
    private String extension;

    @ApiModelProperty(value = "경로", position = 0)
    private String path;

    @ApiModelProperty(value = "파일컨텐츠타입", position = 0)
    private String contentType;

    @ApiModelProperty(value = "s3_bucket", position = 0)
    private String s3Bucket;

    @ApiModelProperty(value = "s3_file_key", position = 0)
    private String s3FileKey;

    @ApiModelProperty(value = "매핑완료여부", position = 0)
    private String mappingDone;

}

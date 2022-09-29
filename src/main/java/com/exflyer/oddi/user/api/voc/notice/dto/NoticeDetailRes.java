package com.exflyer.oddi.user.api.voc.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeDetailRes {

    @ApiModelProperty(hidden = true)
    private String seqType;

    @ApiModelProperty(value = "번호", position = 0)
    private String rownum;

    @ApiModelProperty(value = "순번", position = 0)
    private String seq;

    @ApiModelProperty(value = "제목", position = 1)
    private String title;

    @ApiModelProperty(value = "내용", position = 2)
    private String contents;

    @ApiModelProperty(value = "등록날짜", position = 3)
    private String regDate;

    @ApiModelProperty(value = "이미지순번", position = 4)
    private Long fileSeq;

    @ApiModelProperty(value = "이미지패스", position = 5)
    private String filePath;

    @ApiModelProperty(value = "s3 file key", position = 6)
    private String s3FileKey;

    @ApiModelProperty(value = "file name", position = 7)
    private String fileName;

    @ApiModelProperty(value = "file 확장자", position = 8)
    private String fileExtension;

    @ApiModelProperty(value = "file 타입", position = 10)
    private String fileType;

    @ApiModelProperty(value = "이전글순번", position = 11)
    private String preRownum;

    @ApiModelProperty(value = "이전글제목", position = 12)
    private String preTitle;

    @ApiModelProperty(value = "이전글등록날짜", position = 13)
    private String preRegDate;

    @ApiModelProperty(value = "다음글순번", position = 14)
    private String nextRownum;

    @ApiModelProperty(value = "다음글제목", position = 15)
    private String nextTitle;

    @ApiModelProperty(value = "다음글등록날짜", position = 16)
    private String nextRegDate;
}

package com.exflyer.oddi.user.api.voc.inquire.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InquireDetailRes {

    @ApiModelProperty(value = "seq", position = 0)
    private Long seq;

    @ApiModelProperty(value = "문의유형코드", position = 0)
    private String inquiryTypeCode;

    @ApiModelProperty(value = "문의유형타입", position = 0)
    private String inquiryTypeName;

    @ApiModelProperty(value = "제목", position = 0)
    private String title;

    @ApiModelProperty(value = "내용", position = 0)
    private String contents;

    @ApiModelProperty(value = "답변내용", position = 0)
    private String answer;

    @ApiModelProperty(value = "작성자", position = 0)
    private String name;

    @ApiModelProperty(value = "작성일", position = 0)
    private String regDate;

    @ApiModelProperty(value = "답변여부", position = 0)
    private String isAnswer;

    @ApiModelProperty(value = "파일리스트", position = 0)
    private List<InquireImage> imageList;

}

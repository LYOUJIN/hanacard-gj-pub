package com.exflyer.oddi.user.api.voc.inquire.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.annotaions.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class InquireNoMemberReq {

    @NotBlank
    @ApiModelProperty(value = "이름", position = 0)
    private String name;

    @ApiModelProperty(value = "전화번호", position = 0)
    @NotBlank
    @EncryptField
    @PhoneNumber
    private String phoneNumber;

    @ApiModelProperty(value = "email", position = 0)
    @EncryptField
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @ApiModelProperty(value = "제목", position = 0)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "문의내용", position = 0)
    private String contents;

    @ApiModelProperty(value = "등록일", hidden = true)
    private LocalDateTime regDate;

    @ApiModelProperty(value = "질문유형", position = 0)
    private String inquiryTypeCode;

    @ApiModelProperty(value = "FileSeq", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "seq", position = 0, hidden = true)
    private Long seq;
}

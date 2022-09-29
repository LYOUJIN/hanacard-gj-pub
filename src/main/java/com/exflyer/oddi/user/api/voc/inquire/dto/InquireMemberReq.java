package com.exflyer.oddi.user.api.voc.inquire.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import com.exflyer.oddi.user.annotaions.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class InquireMemberReq {

    @ApiModelProperty(value = "seq", position = 0, hidden = true)
    private Long seq;

    @ApiModelProperty(value = "이름", position = 0, hidden = true)
    private String name;

    @ApiModelProperty(value = "사용자아이디", position = 0, hidden = true)
    private String memberId;

    @ApiModelProperty(value = "전화번호", position = 0, hidden = true)
    private String phoneNumber;

    @ApiModelProperty(value = "email", position = 0, hidden = true)
    private String email;

    @ApiModelProperty(value = "사용자알림 확인여부", position = 0, hidden = true)
    private Boolean userCheck;

    @NotBlank
    @ApiModelProperty(value = "제목", position = 0)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "문의내용", position = 0)
    private String contents;

    @ApiModelProperty(value = "질문유형", position = 0)
    private String inquiryTypeCode;

    @ApiModelProperty(value = "FileSeq", position = 0)
    private Long fileSeq;

}

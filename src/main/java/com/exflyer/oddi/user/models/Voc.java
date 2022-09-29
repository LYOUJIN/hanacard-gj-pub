package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.voc.inquire.dto.InquireMemberReq;
import com.exflyer.oddi.user.api.voc.inquire.dto.InquireNoMemberReq;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "voc")
@ApiModel("1:1문의하기")
public class Voc {

    @Id
    @ApiModelProperty(value = "seq", position = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "member_id", position = 0)
    private String memberId;

    @NotBlank
    @ApiModelProperty(value = "name", position = 0)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "phoneNumber", position = 0)
    private String phoneNumber;

    @NotBlank
    @ApiModelProperty(value = "email", position = 0)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "title", position = 0)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "contents", position = 0)
    private String contents;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "reg_date", position = 0)
    private LocalDateTime regDate;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "answer", position = 0)
    private String answer;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "answer_reg_date", position = 0)
    private LocalDateTime answerRegDate;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "answer_reg_id", position = 0)
    private String answerRegId;

    @Column(insertable=true, updatable=false)
    @ApiModelProperty(value = "answer_reg_id", position = 0)
    private String uniqCode;

    @ApiModelProperty(value = "inquiry_type_code", position = 0)
    private String inquiryTypeCode;

    @ApiModelProperty(value = "user_check", position = 0)
    private Boolean userCheck;

    public void setNoMemberReq(InquireNoMemberReq req){
        this.name = req.getName();
        this.phoneNumber = req.getPhoneNumber();
        this.email = req.getEmail();
        this.title = req.getTitle();
        this.contents = req.getContents();
        this.regDate = LocalDateUtils.krNow();
        this.inquiryTypeCode = req.getInquiryTypeCode();
    }

    public void setMemberReq(InquireMemberReq req){
        this.memberId = req.getMemberId();
        this.name = req.getName();
        this.phoneNumber = req.getPhoneNumber();
        this.email = req.getEmail();
        this.title = req.getTitle();
        this.contents = req.getContents();
        this.regDate = LocalDateUtils.krNow();
        this.inquiryTypeCode = req.getInquiryTypeCode();
        this.userCheck = req.getUserCheck();
    }


}

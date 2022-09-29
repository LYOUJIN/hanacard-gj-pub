package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AddMemeberReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private String regId;

    @ApiModelProperty(value = "순번", position = 0)
    private Long seq;

    @ApiModelProperty(value = "회원ID", position = 0)
    private String memberId;

    @ApiModelProperty(value = "법인여부", position = 0)
    private boolean corporation;

    @ApiModelProperty(value = "회사이름", position = 0)
    private String companyName;

    @ApiModelProperty(value = "대표", position = 0)
    private String ceo;

    @ApiModelProperty(value = "사업 면허증 번호", position = 0)
    private String businessLicenseNumber;

    @ApiModelProperty(value = "사업 면허증 파일", position = 0)
    private Long businessLicenseFile;

    @ApiModelProperty(value = "생성 날짜", position = 0)
    private LocalDateTime regDate;

    @ApiModelProperty(value = "변경 날짜", position = 0)
    private LocalDateTime modDate;

    @ApiModelProperty(value = "등록자명", position = 0)
    private String regName;


}

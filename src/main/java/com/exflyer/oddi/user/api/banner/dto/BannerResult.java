package com.exflyer.oddi.user.api.banner.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class BannerResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("순번")
    private Long seq;

    @ApiModelProperty("이름")
    private String name;

    @ApiModelProperty("종류(web, device)")
    private String type;

    @ApiModelProperty("위치 코드(우측, 상단, 하단, 팝업)")
    private String locationCode;

    @ApiModelProperty("설명")
    private String description;

    @ApiModelProperty("이미지 경로")
    private String imagePath;

    @ApiModelProperty("노출 시작 날짜")
    private String expoStartDate;

    @ApiModelProperty("노출 종료 날짜")
    private String expoEndDate;

    @ApiModelProperty("메모")
    private String memo;

    @ApiModelProperty("라우터 링크")
    private String routerLink;

    @ApiModelProperty("버튼 이름")
    private String buttonName;

    @ApiModelProperty("파일경로")
    private String path;

    @ApiModelProperty("파일명")
    private String fileName;
}

package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@ApiModel("배너")
@Table(name = "banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("순번")
    @Column(name = "seq", nullable = false)
    private Long seq;

    @ApiModelProperty("이름")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("종류(web, device)")
    @Column(name = "type")
    private String type;

    @ApiModelProperty("위치 코드(우측, 상단, 하단, 팝업)")
    @Column(name = "location_code")
    private String locationCode;

    @ApiModelProperty("설명")
    @Column(name = "description")
    private String description;

    @ApiModelProperty("이미지 경로")
    @Column(name = "image_path")
    private String imagePath;

    @ApiModelProperty("노출 시작 날짜")
    @Column(name = "expo_start_date")
    private String expoStartDate;

    @ApiModelProperty("노출 종료 날짜")
    @Column(name = "expo_end_date")
    private String expoEndDate;

    @ApiModelProperty("메모")
    @Column(name = "memo")
    private String memo;

    @ApiModelProperty("라우터 링크")
    @Column(name = "router_link")
    private String routerLink;

    @ApiModelProperty("버튼 이름")
    @Column(name = "button_name")
    private String buttonName;

    @ApiModelProperty("파일순번")
    @Column(name = "file_seq")
    private Long fileSeq;
}

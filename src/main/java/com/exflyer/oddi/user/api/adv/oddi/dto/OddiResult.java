package com.exflyer.oddi.user.api.adv.oddi.dto;

import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.models.Youtube;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiResult {

    @ApiModelProperty(value = "순번", position = 0)
    private Long seq;

    @ApiModelProperty(value = "매장 이름", position = 0)
    private String mallName;

    @ApiModelProperty(value = "배지 코드", position = 0)
    private String badgeCode;

    @ApiModelProperty(value = "배지명", position = 0)
    private String badgeName;

    @ApiModelProperty(value = "광고 사례 노출 여부", position = 0)
    private Boolean advCaseExpo;

    @ApiModelProperty(value = "총 슬롯", position = 0)
    private Integer totalSlot;

    @ApiModelProperty(value = "슬롯당 노출 시간", position = 0)
    private Integer slotVideoTime;

    @ApiModelProperty(value = "슬롯당 금액", position = 0)
    private Integer slotPrice;

    @ApiModelProperty(value = "운영 요일", position = 0)
    private String operationWeek;

    @ApiModelProperty(value = "운영 시작 시간", position = 0)
    private String operationStartTime;

    @ApiModelProperty(value = "운영 종료 시간", position = 0)
    private String operationEndTime;

    @ApiModelProperty(value = "일 노출 카운트", position = 0)
    private Integer dayExpoCount;

    @ApiModelProperty(value = "위도", position = 0)
    private String latitude;

    @ApiModelProperty(value = "경도", position = 0)
    private String longitude;

    @ApiModelProperty(value = "그리드 x", position = 0)
    private Integer gridX;

    @ApiModelProperty(value = "그리드 y", position = 0)
    private Integer gridY;

    @ApiModelProperty(value = "파일seq", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "파일경로", position = 0)
    private String path;

    @ApiModelProperty(value = "파일명이름", position = 0)
    private String name;

    @ApiModelProperty(value = "파일확장자", position = 0)
    private String extension;

    @ApiModelProperty(value = "설명", position = 0)
    private String description;

    @ApiModelProperty(value = "요약", position = 0)
    private String summary;

    @ApiModelProperty(value = "광고사례 조회(파트너에게 매핑된 가장최근 VOD조회)", position = 0)
    private Youtube youtube;

    @ApiModelProperty(value = "이미지리스트", position = 0)
    private List<FileListRes> fileList;

}

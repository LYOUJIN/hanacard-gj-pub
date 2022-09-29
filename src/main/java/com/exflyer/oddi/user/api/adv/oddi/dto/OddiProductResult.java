package com.exflyer.oddi.user.api.adv.oddi.dto;

import com.exflyer.oddi.user.models.Files;
import com.exflyer.oddi.user.models.Youtube;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class OddiProductResult {

    @ApiModelProperty(value = "상품순번", position = 0)
    private Long productSeq;

    @ApiModelProperty(value = "광고순번", position = 0)
    private Long advSeq;

    @ApiModelProperty(value = "상품명", position = 0)
    private String name;

    @ApiModelProperty(value = "금액", position = 0)
    private String price;

    @ApiModelProperty(value = "설명", position = 0)
    private String description;

    @ApiModelProperty(value = "배지 코드", position = 0)
    private String badgeCode;

    @ApiModelProperty(value = "배지명", position = 0)
    private String badgeName;

    @ApiModelProperty(value = "광고사례노출", position = 0)
    private String advCaseExpo;

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

    @ApiModelProperty(value = "파일seq", position = 0)
    private Long fileSeq;

    @ApiModelProperty(value = "파일경로", position = 0)
    private String path;

    @ApiModelProperty(value = "파일명이름", position = 0)
    private String fileName;

    @ApiModelProperty(value = "파일확장자", position = 0)
    private String extension;

    @ApiModelProperty(value = "광고사례 조회(파트너에게 매핑된 가장최근 VOD조회)", position = 0)
    private Youtube youtube;

    @ApiModelProperty(value = "파트너정보", position = 0)
    private List<OddiProductPartnerRes> partnerList;

    @ApiModelProperty(value = "이미지리스트", position = 0)
    private List<FileListRes>  fileList;

}

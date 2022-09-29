package com.exflyer.oddi.user.api.adv.subway.dto;

import com.exflyer.oddi.user.api.adv.oddi.dto.FileListRes;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class SubwayLine {


    @ApiModelProperty(value = "파트너순번", hidden = true)
    private Long partnerSeq;

    @ApiModelProperty(value="묶음순번", position = 0, hidden = true)
    private Long productSeq;

    @ApiModelProperty(value = "지하철명")
    private String name;

    @ApiModelProperty(value = "지하철 코드")
    private String code;

    @ApiModelProperty(value = "파일번호")
    private String fileSeq;

    @ApiModelProperty(value = "파일경로")
    private String path;

    @ApiModelProperty(value = "파일명")
    private String fileName;

    @ApiModelProperty(value = "확장자")
    private String extension;

    @ApiModelProperty(value = "뱃지 코드")
    private String badgeCode;

    @ApiModelProperty(value = "지하철 호선 갯수")
    private Integer lineCount;

    @ApiModelProperty(value = "이미지리스트", position = 0)
    private List<FileListRes> fileList;

}

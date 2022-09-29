package com.exflyer.oddi.user.api.adv.adv.dto;

import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.PartnerConfig;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class AdvProductSearchResult {

    @ApiModelProperty(value = "파트너정보", position = 0)
    private List<AdvProductPartnerRes> advList;

    @ApiModelProperty(value = "광고업종리스트", position = 0)
    private List<Code> advTypeCodeList;

    @ApiModelProperty(value = "파트너설정", position = 0)
    private List<PartnerConfigReq> partnerConfig;

    @ApiModelProperty(value = "기본광고파일들", position = 0)
    private List<PartnerFiles> partnerFilesList;

    public AdvProductSearchResult(List<AdvProductPartnerRes> advList, List<Code> advTypeCodeList, List<PartnerConfigReq> partnerConfig,  List<PartnerFiles> fileList) {
        this.advList = advList;
        this.advTypeCodeList = advTypeCodeList;
        this.partnerConfig = partnerConfig;
        this.partnerFilesList = fileList;
    }
}

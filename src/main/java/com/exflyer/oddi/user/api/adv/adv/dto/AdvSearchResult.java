package com.exflyer.oddi.user.api.adv.adv.dto;

import com.exflyer.oddi.user.models.Code;
import com.exflyer.oddi.user.models.PartnerConfig;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class AdvSearchResult {

    @ApiModelProperty(value = "파트너정보", position = 0)
    private List<AdvPartnerRes> advList;

    @ApiModelProperty(value = "광고업종리스트", position = 0)
    private List<Code> advTypeCodeList;

    @ApiModelProperty(value = "파트너설정", position = 0)
    private List<PartnerConfigReq> partnerConfig;

    @ApiModelProperty(value = "회원회사정보", position = 0)
    private MemberCompanyRes memberCompanyInfo;

    @ApiModelProperty(value = "기본광고파일들", position = 0)
    private List<PartnerFiles> partnerFilesList;

    public AdvSearchResult(List<AdvPartnerRes> advList, List<Code> advTypeCodeList, List<PartnerConfigReq> partnerConfig, List<PartnerFiles> partnerFilesList,MemberCompanyRes memberCompanyInfo) {
        this.advList = advList;
        this.advTypeCodeList = advTypeCodeList;
        this.partnerConfig = partnerConfig;
        this.memberCompanyInfo = memberCompanyInfo;
        this.partnerFilesList = partnerFilesList;
    }
}

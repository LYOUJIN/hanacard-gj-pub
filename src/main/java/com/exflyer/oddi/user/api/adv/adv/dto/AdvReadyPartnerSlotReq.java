package com.exflyer.oddi.user.api.adv.adv.dto;

import io.swagger.annotations.ApiModelProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvReadyPartnerSlotReq {

    @ApiModelProperty(value = "광고처 시퀀스", position = 0)
    private Long partnerSeq;

    @ApiModelProperty(value = "광고처 시퀀스 배열", position = 1)
    private List<Long> partnerSeqList;

    @ApiModelProperty(value = "광고 시작일시", position = 2)
    private String startDate;

    @ApiModelProperty(value = "광고 종료일시", position = 3)
    private String endDate;

    @ApiModelProperty(value = "슬롯 사용 기간", position = 4)
    private Integer slotPeriod;

    /**
     * slot 사용기간으로 endDate 산출
     *
     * @return OddiReadyPartnerSlotReq
     * @throws ParseException
     */
    public AdvReadyPartnerSlotReq countEndDate() throws ParseException {
        if (null != this.slotPeriod && 0 < slotPeriod && StringUtils.isBlank(this.endDate)) {
            this.endDate = new SimpleDateFormat("yyyyMMdd").format(
                DateUtils.addDays(new SimpleDateFormat("yyyyMMdd").parse(this.startDate), slotPeriod - 1));
        }

        return this;
    }
}
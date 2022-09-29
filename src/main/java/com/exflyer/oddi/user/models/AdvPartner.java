package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 광고_광고처
 */
@Data
@Entity
@NoArgsConstructor
@ApiModel("광고_광고처")
@Table(name = "adv_partner")
public class AdvPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AdvPartnerPk advPartnerPk;

    /**
     * 광고 슬롯
     */
    @ApiModelProperty("광고 슬롯")
    @Column(name = "request_slot", nullable = false)
    private Integer requestSlot;

    /**
     * 노출여부
     */
    @ApiModelProperty("노출여부")
    @Column(name = "expo")
    private Boolean expo;

    /**
     * 파트너 금액
     */
    @ApiModelProperty("파트너 금액")
    @Column(name = "price", nullable = false)
    private Integer price;

    @ApiModelProperty("광고신청기간(월단위)")
    @Column(name = "period", nullable = false)
    private Integer period;

    public AdvPartner(Integer requestSlot,Integer price,Integer period) {
        this.requestSlot = requestSlot;
        this.expo = true;
        this.price = price;
        this.period = period;
    }
}

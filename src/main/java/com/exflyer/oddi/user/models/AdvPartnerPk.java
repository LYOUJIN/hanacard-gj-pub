package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 광고_광고처
 */
@Data
@Embeddable
@NoArgsConstructor
public class AdvPartnerPk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 광고 순번
     */
    @ApiModelProperty("광고 순번")
    @Column(name = "adv_seq", nullable = false)
    private Long advSeq;

    /**
     * 파트너 순번
     */
    @ApiModelProperty("파트너 순번")
    @Column(name = "partner_seq", nullable = false)
    private Long partnerSeq;

    public AdvPartnerPk (Long seq, Long partnerSeq) {
        this.advSeq = seq;
        this.partnerSeq = partnerSeq;
    }
}

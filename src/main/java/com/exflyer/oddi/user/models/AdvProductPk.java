package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 광고_묶음상품
 */
@Data
@Embeddable
@NoArgsConstructor
public class AdvProductPk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 광고 순번
     */
    @ApiModelProperty("광고 순번")
    @Column(name = "adv_seq", nullable = false)
    private Long advSeq;

    /**
     * 광고 묶음상품 순번
     */
    @ApiModelProperty("광고 묶음상품 순번")
    @Column(name = "product_seq", nullable = false)
    private Long productSeq;

    public AdvProductPk(Long seq, Long productSeq) {
        this.advSeq = seq;
        this.productSeq = productSeq;
    }
}

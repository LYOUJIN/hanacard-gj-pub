package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 광고_광고처
 */
@Data
@Embeddable
@NoArgsConstructor
public class VocFilePk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * voc 순번
     */
    @ApiModelProperty("voc 순번")
    @Column(name = "voc_seq", nullable = false)
    private Long vocSeq;

    /**
     * 파일 순번
     */
    @ApiModelProperty("file 순번")
    @Column(name = "file_seq", nullable = false)
    private Long fileSeq;


    public VocFilePk(long vocSeq, Long fileSeq) {
        this.vocSeq = vocSeq;
        this.fileSeq = fileSeq;
    }
}

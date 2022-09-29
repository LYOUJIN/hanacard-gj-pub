package com.exflyer.oddi.user.models;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Data;

/**
 * 광고 파일
 */
@Data
@Entity
@ApiModel("광고 파일")
@Table(name = "adv_file")
@IdClass(AdvFile.ID.class)
public class AdvFile {

    /**
     * 광고 순번
     */
    @Id
    @ApiModelProperty("광고 순번")
    @Column(name = "adv_seq", nullable = false)
    private Long advSeq;

    /**
     * 파일 순번
     */
    @Id
    @Column(name = "file_seq", nullable = false)
    @ApiModelProperty("파일 순번")
    private Long fileSeq;

    /**
     * 등록 날짜
     */
    @ApiModelProperty("등록 날짜")
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    /**
     * 종류(doc, image, video)
     */
    @Column(name = "type", nullable = false)
    @ApiModelProperty("종류(doc, image, video)")
    private String type;

    /**
     * 노출순서
     */
    @Column(name = "view_order", nullable = false)
    @ApiModelProperty("노출순서")
    private String viewOrder;


    @Data
    static class ID implements Serializable {
        private Long advSeq;
        private Long fileSeq;
    }
}

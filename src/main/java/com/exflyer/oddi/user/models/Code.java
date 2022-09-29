package com.exflyer.oddi.user.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 코드
 */
@Data
@Entity
@ApiModel("코드")
@Table(name = "code")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 코드
     */
    @Id
    @ApiModelProperty("코드")
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 그룹 코드
     */
    @ApiModelProperty("그룹 코드")
    @Column(name = "group_code", nullable = false)
    private String groupCode;

    /**
     * 이름
     */
    @Column(name = "name")
    @ApiModelProperty("이름")
    private String name;

    /**
     * 사용
     */
    @Column(name = "usable")
    @ApiModelProperty("사용")
    private Boolean usable = true;

    /**
     * 정렬
     */
    @ApiModelProperty("정렬")
    @Column(name = "ordering")
    private Integer ordering;


}

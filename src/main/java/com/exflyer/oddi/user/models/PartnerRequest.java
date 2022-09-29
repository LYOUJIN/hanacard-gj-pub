package com.exflyer.oddi.user.models;

import com.exflyer.oddi.user.api.adv.partner.dto.PartnerJoinReq;
import com.exflyer.oddi.user.share.LocalDateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 광고_광고처
 */
@Data
@Entity
@NoArgsConstructor
@ApiModel("파트너 문의하기")
@Table(name = "partner_request")
public class PartnerRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(name = "seq", hidden = true)
    private long seq;

    @ApiModelProperty(name = "name", position = 0)
    private String name;

    @ApiModelProperty(name = "phone_number", position = 0)
    private String phoneNumber;

    @ApiModelProperty(name = "location", position = 0)
    private String location;

    @ApiModelProperty(name = "business", position = 0)
    private String business;

    @ApiModelProperty(name = "contents", position = 0)
    private String contents;

    @ApiModelProperty(name = "memo", hidden = true)
    private String memo;

    @ApiModelProperty(name = "reg_date", position = 0)
    private LocalDateTime regDate;

    @ApiModelProperty(name = "confirm", hidden = true)
    private long confirm;

    @ApiModelProperty(name = "confirm_id", hidden = true)
    private String confirmId;

}



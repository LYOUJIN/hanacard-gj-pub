package com.exflyer.oddi.user.api.mustad.dto;

import com.exflyer.oddi.user.annotaions.EncryptField;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class MustadMemberResult {

       @ApiModelProperty(value = "머스타드토큰", position = 7)
    private String mustadToken;

    @EncryptField
    private String email;

    @ApiModelProperty(value = "오디,토크니토,카카오 등등", position = 8)
    private String provider;

}

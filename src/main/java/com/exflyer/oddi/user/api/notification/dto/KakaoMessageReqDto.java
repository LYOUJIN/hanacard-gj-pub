package com.exflyer.oddi.user.api.notification.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class KakaoMessageReqDto {

    @ApiModelProperty(value = "슈어엠 제공아이디", hidden = true)
    private String usercode;

    @ApiModelProperty(value = "슈어엠 제공 회사코드", hidden = true)
    private String deptcode;

    @ApiModelProperty(value = "발신프로필키(40자)", hidden = true)
    private String yellowid_key;

    @ApiModelProperty(value = "전송할 메세지 배열", hidden = true)
    private List messages;
}

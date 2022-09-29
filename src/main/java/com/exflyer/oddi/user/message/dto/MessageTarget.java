package com.exflyer.oddi.user.message.dto;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTarget {

  @ApiModelProperty(value = "메세지 아이디",  position = 2)
  @SerializedName("message_id")
  private String messageId;

  @ApiModelProperty(value = "수신자",  position = 2)
  private String to;

}

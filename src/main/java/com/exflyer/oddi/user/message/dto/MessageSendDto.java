package com.exflyer.oddi.user.message.dto;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageSendDto {

  @ApiModelProperty(value = "수신자", position = 2)
  private List<MessageTarget> messages;

  @NotNull
  @ApiModelProperty(value = "내용", position = 3)
  private String text;

  @ApiModelProperty(value = "발송시간(YYYYMMDDhhmm, 빈값은 즉시 발송)", position = 5)
  @SerializedName("reserved_time")
  private String reservedTime;

}

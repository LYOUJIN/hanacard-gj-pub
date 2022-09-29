package com.exflyer.oddi.user.message.dto;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class MessageSendReq {

  @ApiModelProperty(value = "usercode", position = 0, hidden = true)
  private String usercode;

  @ApiModelProperty(value = "deptcode", position = 1, hidden = true)
  private String deptcode;

  @ApiModelProperty(value = "수신자", position = 2)
  private List<MessageTarget> messages;

  @NotNull
  @ApiModelProperty(value = "내용", position = 3)
  private String text;

  @NotNull
  @ApiModelProperty(value = "발신자", position = 4)
  private String from;

  @ApiModelProperty(value = "발송시간(YYYYMMDDhhmm, 빈값은 즉시 발송)", position = 5)
  @SerializedName("reserved_time")
  private String reservedTime;

  public MessageSendReq(MessageSendDto messageSendDto) {
    this.messages = messageSendDto.getMessages();
    this.text = messageSendDto.getText();
    if (StringUtils.isNotBlank(messageSendDto.getReservedTime())) {
      this.reservedTime = messageSendDto.getReservedTime();
    }
  }
}

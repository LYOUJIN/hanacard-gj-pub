package com.exflyer.oddi.user.api.live.dto;

import com.exflyer.oddi.user.models.LiveSchedule;
import com.exflyer.oddi.user.models.LiveStreaming;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LiveStreamDetailResult {

  private LiveStreaming liveStreamResult;
  private LiveSchedule liveScheduleResult;

  public LiveStreamDetailResult(LiveStreaming liveStreamResult, LiveSchedule liveScheduleResult){
    this.liveStreamResult = liveStreamResult;
    this.liveScheduleResult = liveScheduleResult;
  }

}

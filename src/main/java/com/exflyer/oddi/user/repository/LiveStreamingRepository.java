package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LiveStreamingRepository extends JpaRepository<LiveStreaming, Long>, JpaSpecificationExecutor<LiveStreaming> {

    @Query(value = "select * from live_streaming limit 1", nativeQuery = true)
    LiveStreaming findByChannelInfo();

}

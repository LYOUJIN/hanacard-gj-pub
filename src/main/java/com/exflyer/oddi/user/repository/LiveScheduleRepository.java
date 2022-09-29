package com.exflyer.oddi.user.repository;

import com.exflyer.oddi.user.models.LiveSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LiveScheduleRepository extends JpaRepository<LiveSchedule, Long>, JpaSpecificationExecutor<LiveSchedule> {

    @Query(value = "select * from live_schedule where operation_day = :nowDate and start_time <= :nowTime and end_time > :nowTime", nativeQuery = true)
    LiveSchedule findByScheduleInfo(@Param("nowDate") String nowDate, @Param("nowTime") String nowTime);

    @Query(value = "select "
        + "seq"
        + ", title"
        + ", concat(date_format(str_to_date(operation_day, '%Y%m%d'),'%Y-%m-%d '), ' ', substring(start_time, 1, 2), ':', substring(start_time, 3, 2), '~', substring(end_time, 1, 2), ':', substring(end_time, 3, 2)) as operation_day"
        + ", start_time"
        + ", end_time"
        + ", reg_date"
        + ", reg_id"
        + ", mod_date"
        + ", mod_id"
        + " from live_schedule where operation_day >= :nowDate", nativeQuery = true)
    List<LiveSchedule> findByScheduleList(@Param("nowDate") String nowDate);

}

package com.exflyer.oddi.user.share;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalDateUtils {

    private static String timeZone = "Asia/Seoul";

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Value("${timezone}")
    private void setTimeZone(String timeZone){
    this.timeZone = timeZone;
    }

    public static LocalDateTime krNow(){
    return LocalDateTime.now(ZoneId.of(timeZone));
    }

    public static String krNowByFormatter(String formatter){
    return LocalDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(formatter));
    }

    public static LocalDate krNowLocalDate() {
    return LocalDate.now(ZoneId.of(timeZone));
    }

    public static String addkrNow(Integer day){
    return LocalDateUtils.krNow().plusDays(day).format(formatter);
    }

    public static String dateConvertFormatter(){
        return LocalDateUtils.krNow().format(formatter);
    }

    public static Integer diffMonth(String toDateStr, String fromDateStr){

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        long diffMonth = 0L;
        try{

            Date toDate = format.parse(toDateStr);
            Date fromDate = format.parse(fromDateStr);

            long baseDay = 24 * 60 * 60 * 1000; 	// 일
            long baseMonth = baseDay * 30;		// 월
            long baseYear = baseMonth * 12;		// 년

            // from 일자와 to 일자의 시간 차이를 계산한다.
            long calDate = fromDate.getTime() - toDate.getTime();

            // from 일자와 to 일자의 시간 차 값을 하루기준으로 나눠 준다.
            diffMonth = Math.round( ((double) calDate) / baseMonth);


        }catch(Exception e) {
            // TODO: handle exception
        }
        return (int)diffMonth;
    }

}

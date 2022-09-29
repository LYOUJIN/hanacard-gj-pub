package com.exflyer.oddi.user.api.payment.service;


import java.lang.reflect.Field;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InicisPaymentLogService {

//    public void paymentCancelLog(Map<String, String> map) {
//        log.debug("============= [InicisPaymentLogService START] ========================");
//        for(String key : map.keySet()) {
//            log.debug("key: {}, value : {}", key, map.get(key));
//        }
//        log.debug("============= [InicisPaymentLogService END] ========================");
//    }
//
    public void paymentLog(String key, String value) {
        log.info("key: {}, value : {}", key, value);
    }

    public void paymentLog(String value) {
        log.info(value);
    }

    public void paymentLog(Object vo) throws IllegalAccessException {
        for(Field field : vo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(vo);
            log.debug("key: {}, value : {}", field.getName(), value);
        }
    }
}

package com.exflyer.oddi.user.api.test.service;

import com.exflyer.oddi.user.config.KakaoNotificationConfig;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.KakaoTemplate;
import com.exflyer.oddi.user.models.Notification;
import com.exflyer.oddi.user.models.NotificationGroup;
import com.exflyer.oddi.user.repository.KakaoTemplateRepository;
import com.exflyer.oddi.user.repository.NotificationGroupRepository;
import com.exflyer.oddi.user.repository.NotificationRepository;
import com.exflyer.oddi.user.share.LocalDateUtils;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @user : 2022-07-07
 * @test
 */
@Component
@Slf4j
public class TestService {

    @Autowired
    private KakaoNotificationConfig kakaoNotificationConfig;

    @Autowired
    private KakaoTemplateRepository kakaoTemplateRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationGroupRepository notificationGroupRepository;

    @Value("${oddi.host}")
    private String hostUrl;


    @PersistenceContext
    EntityManager em;

    @Transactional
    public void sendKakaoTemplate(String templateId)  throws ApiException {

        Optional<KakaoTemplate> kakaoTemplateOptional = kakaoTemplateRepository.findById(templateId);
        KakaoTemplate kakaoTemplate = kakaoTemplateOptional.orElseThrow(() -> new ApiException(
            ApiResponseCodes.NOT_FOUND));


        Notification notification = new Notification();
        notification.setAlrimTalk(true);
        notification.setContents(kakaoTemplate.getTemplateContents());
        notification.setSenderId(kakaoNotificationConfig.getReSendFromId());
        notification.setSenderName(kakaoNotificationConfig.getReSendFromName());
        notification.setReceiveId("test");
        notification.setReceiveName("테스트이름");
        notification.setReceivePhoneNumber("01047432141"); //수신자번호
        notification.setKakaoTemplateId(kakaoTemplate.getTemplateId());
        notificationRepository.save(notification);

    }

    @Transactional
    public void sendGroupKakaoTemplate(String templateId)  throws ApiException {

        Optional<KakaoTemplate> kakaoTemplateOptional = kakaoTemplateRepository.findById(templateId);
        KakaoTemplate kakaoTemplate = kakaoTemplateOptional.orElseThrow(() -> new ApiException(
            ApiResponseCodes.NOT_FOUND));

        NotificationGroup notificationGroup = new NotificationGroup();
        notificationGroup.setContents(kakaoTemplate.getTemplateContents());
        notificationGroup.setTemplateId(kakaoTemplate.getTemplateId());
        notificationGroup.setTargetGroupSeq(0L);
        notificationGroup.setAlrimTalk(true);
        notificationGroup.setAdvMessage(false);
        notificationGroup.setAuto(false);
        notificationGroup.setSenderPhoneNumber("01047432141");
        notificationGroup.setDone(false);
        notificationGroup.setRegId("test");
        notificationGroup.setRegDate(LocalDateUtils.krNow());
        notificationGroupRepository.save(notificationGroup);
        em.persist(notificationGroup);

        Notification notification = new Notification();
        notification.setGroupSeq(0L);
        notification.setContents(kakaoTemplate.getTemplateContents());
        notification.setSendTime(false);
        notification.setSenderPhoneNumber("01047432141");
        notification.setAlrimTalk(true);
        notification.setRegDate(LocalDateUtils.krNow());
        notification.setKakaoTemplateId(kakaoTemplate.getTemplateId());
        notification.setSenderId("test");
        notification.setSenderName("오디 관리자");
        notification.setReceiveId("yj.lee");
        notification.setReceiveName("테스트이름");
        notification.setReceivePhoneNumber("01047432141"); //수신자번호
        notificationRepository.save(notification);
}
}

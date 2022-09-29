package com.exflyer.oddi.user.api.notification.service;

import com.exflyer.oddi.user.api.notification.dto.KakaoMessageBody;
import com.exflyer.oddi.user.api.notification.dto.KakaoMessageReqDto;
import com.exflyer.oddi.user.api.notification.dto.KakaoNotificationReq;
import com.exflyer.oddi.user.api.notification.dto.KakaoNotificationRes;
import com.exflyer.oddi.user.api.payment.dao.PaymentMapper;
import com.exflyer.oddi.user.api.payment.dto.AdvUserCancelReq;
import com.exflyer.oddi.user.config.KakaoNotificationConfig;
import com.exflyer.oddi.user.enums.ApiResponseCodes;
import com.exflyer.oddi.user.enums.KakaoNoticationCodes;
import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.KakaoTemplate;
import com.exflyer.oddi.user.models.Member;
import com.exflyer.oddi.user.models.Notification;
import com.exflyer.oddi.user.repository.KakaoTemplateRepository;
import com.exflyer.oddi.user.repository.NotificationRepository;
import com.exflyer.oddi.user.repository.PartnerRequestRepository;
import com.exflyer.oddi.user.repository.VocRepository;
import com.exflyer.oddi.user.repository.jpa.MemberRepository;
import com.exflyer.oddi.user.share.AesEncryptor;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class KakaoNotificationService {

    @Autowired
    private KakaoNotificationConfig kakaoNotificationConfig;

    @Autowired
    private KakaoTemplateRepository kakaoTemplateRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private VocRepository vocRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PartnerRequestRepository partnerRequestRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AesEncryptor aesEncryptor;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Gson gson;

    @Value("${oddi.host}")
    private String hostUrl;

    public KakaoNotificationRes reqNotification(KakaoNotificationReq kakaoNotificationReq) throws ApiException, IOException {

        //TEST
        //Optional<KakaoTemplate> kakaoTemplateOptional = kakaoTemplateRepository.findById("ex_test001");
        //KakaoTemplate kakaoTemplate = kakaoTemplateOptional.orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

        KakaoMessageReqDto kakaoMessageReqDto = new KakaoMessageReqDto();
        kakaoMessageReqDto.setUsercode(kakaoNotificationConfig.getId());
        kakaoMessageReqDto.setDeptcode(kakaoNotificationConfig.getDeptCode());
        kakaoMessageReqDto.setYellowid_key(kakaoNotificationConfig.getSendProfileKey());

        KakaoMessageBody msgBody = new KakaoMessageBody();
        msgBody.setMessage_id(UUID.randomUUID().toString().replace("-", ""));
        msgBody.setRe_send(kakaoNotificationReq.getReSend());
        msgBody.setTo(kakaoNotificationReq.getTo());
        msgBody.setTemplate_code(kakaoNotificationReq.getTemplateCode());
        msgBody.setFrom(kakaoNotificationConfig.getReSendFromPhoneNum());
        msgBody.setText(kakaoNotificationReq.getText());

        //TEST
        //msgBody.setText(kakaoTemplate.getTemplateContents().replace("#{고객명}", "오디테스터"));

        kakaoMessageReqDto.setMessages(Arrays.asList(msgBody));

        HttpPost httpPost = new HttpPost(kakaoNotificationConfig.getApiUrl());
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setEntity(new StringEntity(new Gson().toJson(kakaoMessageReqDto), "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        String resBody = EntityUtils.toString(response.getEntity());

        return gson.fromJson(resBody, KakaoNotificationRes.class);
    }

    @Transactional
    public void sendKakaoPassword(Member member, String initPassword) throws ApiException {

        String templateCode = KakaoNoticationCodes.USER_PW_CHANGE.getCode();
        Optional<KakaoTemplate> kakaoTemplateOptional = kakaoTemplateRepository.findById(templateCode);
        KakaoTemplate kakaoTemplate = kakaoTemplateOptional.orElseThrow(() -> new ApiException(
            ApiResponseCodes.NOT_FOUND));

        String contents = kakaoTemplate.getTemplateContents()
            .replace("#{고객명}", member.getName())
            .replace("#{초기화비밀번호}", initPassword)
            .replace("#{홈페이지 url}", hostUrl);

        //그룹에 먼저 저장
        // 2021.12.22 그룹 seq 가 0 이라면 발송을 하고 이력에 남기질 않음
        // 비밀번호 초기화 같은 경우는 이력에 남길 필요가 없음

        Notification notification = new Notification();
        notification.setAlrimTalk(true);
        notification.setContents(contents);
        notification.setSenderId(kakaoNotificationConfig.getReSendFromId());
        notification.setSenderName(kakaoNotificationConfig.getReSendFromName());
        notification.setReceiveId(member.getId());
        notification.setReceiveName(member.getName());
        notification.setReceivePhoneNumber(aesEncryptor.decrypt(member.getPhoneNumber())); //수신자번호
        notification.setKakaoTemplateId(templateCode);
        notificationRepository.save(notification);
    }

    @Transactional
    public void sendKakaoAdvCancel(Long advSeq) throws ApiException {

        AdvUserCancelReq userInfo = paymentMapper.findAdvCancelUserInfo(advSeq);

        String templateCode = KakaoNoticationCodes.ADV_CANCEL.getCode();
        Optional<KakaoTemplate> kakaoTemplateOptional = kakaoTemplateRepository.findById(templateCode);
        KakaoTemplate kakaoTemplate = kakaoTemplateOptional
          .orElseThrow(() -> new ApiException(ApiResponseCodes.NOT_FOUND));

        try{

            String contents = kakaoTemplate.getTemplateContents()
                .replace("#{고객이름}", userInfo.getName())
                .replace("#{광고 이름}", userInfo.getTitle())
                .replace("#{신청 일시}", userInfo.getRegDate())
                .replace("#{광고시작일}", userInfo.getStartDate())
                .replace("#{광고종료일}", userInfo.getEndDate())
                .replace("#{환불 금액}", userInfo.getPrice())
                .replace("#{홈페이지 url}", hostUrl);

            Notification notification = new Notification();
            notification.setAlrimTalk(true);
            notification.setContents(contents);
            notification.setSenderId(kakaoNotificationConfig.getReSendFromId());
            notification.setSenderName(kakaoNotificationConfig.getReSendFromName());
            notification.setReceiveId(userInfo.getId());
            notification.setReceiveName(userInfo.getName());
            notification.setReceivePhoneNumber(aesEncryptor.decrypt(userInfo.getPhoneNumber())); //수신자번호
            notification.setKakaoTemplateId(templateCode);
            notificationRepository.saveAndFlush(notification);

        }catch(Exception e) {
            log.debug("============================[ sendKakaoAdvCancel Exception START ]===========================");
            log.debug("sendKakaoAdvCancel {},{},{},{}, e : {}",  userInfo.getId(), userInfo.getName(), userInfo.getTitle(), userInfo.getRegDate(), e);
            log.debug("============================[ sendKakaoAdvCancel Exception END ]===========================");
        }
    }
}

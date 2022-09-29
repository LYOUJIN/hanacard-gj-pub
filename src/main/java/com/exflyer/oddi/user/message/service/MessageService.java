package com.exflyer.oddi.user.message.service;

import com.exflyer.oddi.user.exceptions.ApiException;
import com.exflyer.oddi.user.models.Notification;
import com.exflyer.oddi.user.repository.NotificationRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageService {

  @Autowired
  private NotificationRepository notificationRepository;

  public void send(String message, String phoneNumber, String sendName) throws IOException, ApiException {
    Notification notification = new Notification(message, phoneNumber,sendName);
    notificationRepository.save(notification);
  }



}

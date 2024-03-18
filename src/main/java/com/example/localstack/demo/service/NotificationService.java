package com.example.localstack.demo.service;

import com.example.localstack.demo.config.EventsConfig;
import com.example.localstack.demo.domain.NotificationMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationService {

  private final EventsConfig config;
  @Autowired
  private NotificationMessagingTemplate notificationTemplate;
  @Autowired
  private QueueMessagingTemplate messagingTemplate;

  public void notifyTopic(NotificationMessage message) {
    log.info("START >> Notifying topic {}", config.getTopic());
    notificationTemplate.sendNotification(config.getTopic(), message, "notification");
    log.info("END >> Notified topic {}, payload: {}", config.getTopic(), message);
  }
  public void notifyQueue(NotificationMessage message) {
    log.info("START >> Notifying queue {}", config.getTopic());
    log.info("Notifying queue {}", config.getQueue());
    messagingTemplate.convertAndSend(config.getQueue(), message);
    log.info("END >> Notified queue {}, payload: {}", config.getTopic(), message);
  }
}
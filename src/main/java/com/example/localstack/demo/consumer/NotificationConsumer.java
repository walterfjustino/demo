package com.example.localstack.demo.consumer;

import com.example.localstack.demo.config.EventsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {

  private final EventsConfig config;

  public NotificationConsumer(EventsConfig config) {
    this.config = config;
  }

  @SqsListener(value = "${events.queue}")
  public void consumeQueue(Message<String> message) {
    log.info("START >> consuming queue: {}", config.getQueue());
    log.info("message consumed {}", message.getPayload());
    log.info("END >> consuming queue: {}", config.getQueue() );
  }

  @SqsListener(value = "${events.topic}")
  public void consumeTopic(Message<String> message) {
    log.info("START >> consuming topic: {}", config.getTopic());
    log.info("message consumed {}", message.getPayload());
    log.info("END >> consuming topic: {}", config.getTopic() );
  }
}

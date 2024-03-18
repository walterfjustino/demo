package com.example.localstack.demo.controller;

import com.example.localstack.demo.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/notify")
public class NotificationController {

  @Autowired
  private NotificationService service;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/topic")
  public void topic(@RequestBody NotificationRequest request) {
    log.info("sending message toward SNS...");
    service.notifyTopic(request.toDomain());
    log.info("Message sent with body: {}", request.toDomain());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/queue")
  public void queue(@RequestBody NotificationRequest request) {
    log.info("sending message toward SQS...");
    service.notifyQueue(request.toDomain());
    log.info("Message sent with body: {}", request.toDomain());
  }
}
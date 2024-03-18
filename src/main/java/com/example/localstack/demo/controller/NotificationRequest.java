package com.example.localstack.demo.controller;

import com.example.localstack.demo.domain.NotificationMessage;

record NotificationRequest(String from, String to, String content) {

  public NotificationMessage toDomain() {
    return new NotificationMessage(this.from, this.to, this.content);
  }
}
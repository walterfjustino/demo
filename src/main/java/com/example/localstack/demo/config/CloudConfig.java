package com.example.localstack.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;

public abstract class CloudConfig {

  @Value("${cloud.aws.credentials.access-key}")
  protected String accessKeyId;

  @Value("${cloud.aws.credentials.secret-key}")
  protected String secretAccessKey;

  @Value("${cloud.aws.region.static}")
  protected String region;

  AWSStaticCredentialsProvider getCredentialsProvider() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
  }
}

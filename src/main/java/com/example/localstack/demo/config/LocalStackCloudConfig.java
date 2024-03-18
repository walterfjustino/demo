package com.example.localstack.demo.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LocalStackCloudConfig extends CloudConfig {

  // Recuperando valores configurados do application.yml
  @Value("${cloud.aws.endpoint.uri}")
  private String host;

  // Configurações dos beans para o SNS e SQS

  @Bean
  @Primary
  public AmazonSQSAsync amazonSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(getEndpointConfiguration())
            .withCredentials(getCredentialsProvider())
            .build();
  }

  @Bean
  public AmazonSNS amazonSNSAsync() {
    return AmazonSNSAsyncClientBuilder.standard()
            .withEndpointConfiguration(getEndpointConfiguration())
            .withCredentials(getCredentialsProvider())
            .build();
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate( AmazonSQSAsync amazonSQSAsync) {
    return new QueueMessagingTemplate(amazonSQSAsync);
  }

  @Bean
  public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
    return new NotificationMessagingTemplate(amazonSNS);
  }

  /** This bean is responsible for listening to the queue and must be defined. */
  /**
   * Esse bean é responsável por ouvir a fila e PRECISA ser definido.
   */
  @Bean
  public QueueMessageHandler queueMessageHandler() {
    var queueMessageHandlerFactory = new QueueMessageHandlerFactory();
    queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync());
    return queueMessageHandlerFactory.createQueueMessageHandler();
  }


  /** This bean is responsible for listening to the queue and must be defined. */
  /**
   * Esse bean é responsável por ouvir a fila e PRECISA ser definido.
   */
  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer() {
    var simpleListenerContainer = new SimpleMessageListenerContainer();
    simpleListenerContainer.setAmazonSqs(amazonSQSAsync());
    simpleListenerContainer.setMessageHandler(queueMessageHandler());
    return simpleListenerContainer;
  }

  private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
    return new AwsClientBuilder.EndpointConfiguration(host, region);
  }
}

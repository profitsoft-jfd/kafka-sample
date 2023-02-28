package dev.profitsoft.jfd.kafkasample.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value("${kafka.topic.paymentReceived}")
  private String paymentReceivedTopic;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = Map.of(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic paymentReceivedTopic() {
    return new NewTopic(paymentReceivedTopic, 2, (short) 1);
  }
}

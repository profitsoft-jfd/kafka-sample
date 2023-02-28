package dev.profitsoft.jfd.kafkasample.controller;

import dev.profitsoft.jfd.kafkasample.dto.PaymentReceivedDto;
import dev.profitsoft.jfd.kafkasample.messaging.PaymentReceivedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * In real app this class could be a part of other microservice.
 * It demonstrates how a message can be sent to a kafka topic.
 */
@RestController
@RequiredArgsConstructor
public class PaymentController {

  @Value("${kafka.topic.paymentReceived}")
  private String paymentReceivedTopic;

  private final KafkaOperations<String, PaymentReceivedMessage> kafkaOperations;

  @PostMapping("/paymentConfirmation")
  public void receivePayment(@RequestBody PaymentReceivedDto dto) {
    // As a message key we use orderId, so all messages related to
    // one Order will be routed to one topic partition and processed sequentially
    kafkaOperations.send(paymentReceivedTopic, dto.getOrderId(), toMessage(dto));
  }

  private static PaymentReceivedMessage toMessage(PaymentReceivedDto dto) {
    return PaymentReceivedMessage.builder()
        .orderId(dto.getOrderId())
        .sum(dto.getSum())
        .transactionId(UUID.randomUUID().toString())
        .build();
  }

}

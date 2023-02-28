package dev.profitsoft.jfd.kafkasample.listener;

import dev.profitsoft.jfd.kafkasample.messaging.PaymentReceivedMessage;
import dev.profitsoft.jfd.kafkasample.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentReceivedListener {

  private final OrderService orderService;

  @KafkaListener(topics = "${kafka.topic.paymentReceived}")
  public void paymentReceived(PaymentReceivedMessage message) {
    orderService.processPaymentReceived(message);
  }

}

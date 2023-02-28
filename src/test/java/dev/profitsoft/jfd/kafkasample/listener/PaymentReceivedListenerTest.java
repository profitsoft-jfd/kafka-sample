package dev.profitsoft.jfd.kafkasample.listener;

import dev.profitsoft.jfd.kafkasample.data.OrderStatus;
import dev.profitsoft.jfd.kafkasample.dto.OrderDetailsDto;
import dev.profitsoft.jfd.kafkasample.dto.OrderSaveDto;
import dev.profitsoft.jfd.kafkasample.messaging.PaymentReceivedMessage;
import dev.profitsoft.jfd.kafkasample.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@Slf4j
@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class PaymentReceivedListenerTest {

  @Value("${kafka.topic.paymentReceived}")
  private String paymentReceivedTopic;

  @Autowired
  KafkaOperations<String, PaymentReceivedMessage> kafkaOperations;

  @SpyBean
  private OrderService orderService;

  @Test
  void testPaymentReceivedAndPayed() {
    String orderId = createOrder(1000.0);

    double payedOffSum = 1000.0;
    PaymentReceivedMessage message = PaymentReceivedMessage.builder()
        .orderId(orderId)
        .sum(payedOffSum)
        .transactionId("111")
        .build();
    kafkaOperations.send(paymentReceivedTopic, orderId, message);

    // As orderService is marked as a @SpyBean, we can use method verify(..) with timeout
    // to wait until method processPaymentReceived is executed
    verify(orderService, after(5000)).processPaymentReceived(any());

    OrderDetailsDto orderDetails = orderService.getOrderDetails(orderId);
    assertThat(orderDetails.getPayedOffSum()).isEqualTo(payedOffSum);
    assertThat(orderDetails.getStatus()).isEqualTo(OrderStatus.PAYED);
  }

  private String createOrder(double sum) {
    OrderSaveDto orderSaveDto = OrderSaveDto.builder()
        .sum(sum)
        .build();
    return orderService.createOrder(orderSaveDto);
  }

}

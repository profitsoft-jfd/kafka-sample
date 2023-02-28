package dev.profitsoft.jfd.kafkasample.service;

import dev.profitsoft.jfd.kafkasample.dto.OrderDetailsDto;
import dev.profitsoft.jfd.kafkasample.dto.OrderSaveDto;
import dev.profitsoft.jfd.kafkasample.messaging.PaymentReceivedMessage;

public interface OrderService {

  String createOrder(OrderSaveDto dto);

  OrderDetailsDto getOrderDetails(String orderId);

  void processPaymentReceived(PaymentReceivedMessage message);

}

package dev.profitsoft.jfd.kafkasample.service;

import dev.profitsoft.jfd.kafkasample.data.OrderData;
import dev.profitsoft.jfd.kafkasample.data.OrderStatus;
import dev.profitsoft.jfd.kafkasample.dto.OrderDetailsDto;
import dev.profitsoft.jfd.kafkasample.dto.OrderSaveDto;
import dev.profitsoft.jfd.kafkasample.exceptions.NotFoundException;
import dev.profitsoft.jfd.kafkasample.messaging.PaymentReceivedMessage;
import dev.profitsoft.jfd.kafkasample.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  public static final double SUM_TOLERANCE = 0.001;

  private final OrderRepository orderRepository;

  @Override
  public String createOrder(OrderSaveDto dto) {
    OrderData order = new OrderData();
    order.setSum(dto.getSum());
    order.setStatus(OrderStatus.NEW);
    OrderData saved = orderRepository.save(order);
    return saved.getId();
  }

  @Override
  public OrderDetailsDto getOrderDetails(String orderId) {
    OrderData data = getOrThrow(orderId);
    return OrderDetailsDto.builder()
        .id(data.getId())
        .sum(data.getSum())
        .status(data.getStatus())
        .payedOffSum(data.getPayedOffSum())
        .build();
  }

  @Override
  public void processPaymentReceived(PaymentReceivedMessage message) {
    OrderData order = getOrThrow(message.getOrderId());
    if (!order.hasTransactionId(message.getTransactionId())) {
      order.addPayedOffSum(message.getSum());
      order.addTransactionId(message.getTransactionId());
      order.setStatus(defineOrderStatus(order));
      orderRepository.save(order);
    }
  }

  private OrderData getOrThrow(String orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order with id '%s' not found".formatted(orderId)));
  }

  private OrderStatus defineOrderStatus(OrderData order) {
    if (order.getPayedOffSum() == null || order.getPayedOffSum() <= 0) {
      return OrderStatus.NEW;
    }
    if (order.getSum() - order.getPayedOffSum() >= SUM_TOLERANCE) {
      return OrderStatus.PARTIALLY_PAYED;
    }
    return OrderStatus.PAYED;
  }

}

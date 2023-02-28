package dev.profitsoft.jfd.kafkasample.controller;

import dev.profitsoft.jfd.kafkasample.dto.OrderDetailsDto;
import dev.profitsoft.jfd.kafkasample.dto.OrderSaveDto;
import dev.profitsoft.jfd.kafkasample.dto.RestResponse;
import dev.profitsoft.jfd.kafkasample.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{id}")
  public OrderDetailsDto getOrderDetails(@PathVariable("id") String id) {
    return orderService.getOrderDetails(id);
  }

  @PostMapping
  public RestResponse createOrder(@RequestBody OrderSaveDto dto) {
    return new RestResponse(orderService.createOrder(dto));
  }

}

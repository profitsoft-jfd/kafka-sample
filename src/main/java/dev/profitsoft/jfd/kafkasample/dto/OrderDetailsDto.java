package dev.profitsoft.jfd.kafkasample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.profitsoft.jfd.kafkasample.data.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailsDto {

  private String id;

  private OrderStatus status;

  private Double sum;

  private Double payedOffSum;

}

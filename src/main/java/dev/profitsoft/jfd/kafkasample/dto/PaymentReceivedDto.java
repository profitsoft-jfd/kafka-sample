package dev.profitsoft.jfd.kafkasample.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PaymentReceivedDto {

  private String orderId;

  private Double sum;

}

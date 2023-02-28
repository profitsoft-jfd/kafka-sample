package dev.profitsoft.jfd.kafkasample.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PaymentReceivedMessage {

  private String transactionId;

  private String orderId;

  private Double sum;

}

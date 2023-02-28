package dev.profitsoft.jfd.kafkasample.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderData {

  @Id
  private String id;

  private OrderStatus status;

  private Double sum;

  private Double payedOffSum;

  private List<String> transactionIds;

  public boolean hasTransactionId(String transactionId) {
    return transactionIds != null && transactionIds.contains(transactionId);
  }

  public void addPayedOffSum(Double payedOffSum) {
    this.payedOffSum = this.payedOffSum != null
        ? this.payedOffSum + payedOffSum
        : payedOffSum;
  }

  public void addTransactionId(String transactionId) {
    if (this.transactionIds == null) {
      this.transactionIds = new ArrayList<>(2);
    }
    this.transactionIds.add(transactionId);
  }

}

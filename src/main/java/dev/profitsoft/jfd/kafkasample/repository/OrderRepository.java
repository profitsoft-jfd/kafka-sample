package dev.profitsoft.jfd.kafkasample.repository;

import dev.profitsoft.jfd.kafkasample.data.OrderData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderData, String> {

}

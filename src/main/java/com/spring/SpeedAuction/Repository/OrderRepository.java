package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.OrderModels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderModels, String> {

}

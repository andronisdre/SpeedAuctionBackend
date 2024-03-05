package com.spring.SpeedAuction.Repository;


import com.spring.SpeedAuction.Models.OrderModels;
import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface OrderRepository extends MongoRepository<OrderModels, String> {

}

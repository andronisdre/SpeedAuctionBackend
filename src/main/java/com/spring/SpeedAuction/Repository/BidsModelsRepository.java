package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.BidsModels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BidsModelsRepository extends MongoRepository<BidsModels,String> {
}

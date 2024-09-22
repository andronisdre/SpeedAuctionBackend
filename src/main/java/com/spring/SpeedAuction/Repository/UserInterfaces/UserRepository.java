package com.spring.SpeedAuction.Repository.UserInterfaces;

import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModels, String> {
}
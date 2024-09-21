package com.spring.SpeedAuction.Repository.UserInterfaces;

import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FindUserFilter extends MongoRepository<UserModels, String> {
    Optional<UserModels> findByUsername(String username);
}

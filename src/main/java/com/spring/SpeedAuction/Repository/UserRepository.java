package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModels, String> {

    Optional<UserModels> findByUsername(String username);

    Boolean existsByUsername(String username);

}

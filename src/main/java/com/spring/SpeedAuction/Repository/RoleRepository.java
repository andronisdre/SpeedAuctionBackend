package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.ERole;
import com.spring.SpeedAuction.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface   RoleRepository extends MongoRepository<Role, String> {

    Optional<Role>findByName(ERole name);

}

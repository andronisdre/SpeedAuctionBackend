package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.ERole;
import com.spring.SpeedAuction.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface   RoleRepository extends MongoRepository<Role, String> {

    Optional<Role>findByName(ERole name);

}

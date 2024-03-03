package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.ERole;
import com.spring.SpeedAuction.Models.Role;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.RoleRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.payload.request.SignupRequest;
import com.spring.SpeedAuction.payload.response.MessageResponse;
import com.spring.SpeedAuction.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    //geistera en user
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        // KOLLAR OM USERN FINNS
        if (userRepository.existByUsername((signupRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error username already exist!"));
        }

        //SKAPA KONTO IF FALSE
        UserModels user = new UserModels(
                signupRequest.getUsername(),
                signupRequest.getFirst_name(),
                signupRequest.getLast_name(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail(),
                signupRequest.getPhone_number(),
                signupRequest.getAddress(),
                signupRequest.getCountry(),
                signupRequest.getCity(),
                signupRequest.getPostal_code());

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error: roles is not found"));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                if (role.equals("amin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found"));

                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }


}

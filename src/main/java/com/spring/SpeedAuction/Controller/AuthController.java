package com.spring.SpeedAuction.Controller;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
                signupRequest.getPostal_code()



        );

    }


}

package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.ERole;
import com.spring.SpeedAuction.Models.Role;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.RoleRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.Services.UserDetailImpl;
import com.spring.SpeedAuction.payload.request.SigninRequest;
import com.spring.SpeedAuction.payload.request.SignupRequest;
import com.spring.SpeedAuction.payload.response.MessageResponse;
import com.spring.SpeedAuction.payload.response.UserInfoResponse;
import com.spring.SpeedAuction.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    //logga in
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //jwt utan cookie
        //String jwt = jwtUtils.generateJwtToken((authentication));

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();

        //for jwt i cookie
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //jwt utan cookie

        /*
        return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getID(),
        userDetails.getUsername();
        userDetail.getEmail(),
        roles));
         */

        //Jwt med cookie
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }


    //geistera en user
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        // KOLLAR OM USERN FINNS
        if (userRepository.existsByUsername((signupRequest.getUsername()))) {
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

        if (strRoles == null) {

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: roles is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "amin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modeRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                    }
                    default -> {

                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }


}

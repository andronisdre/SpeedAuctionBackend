package com.spring.SpeedAuction.Controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Api test
@RestController
@RequestMapping("/api/test")
public class TestController {

    //public route öppen för alla
    @GetMapping("/all")
    public String allAccess(){
        return "Public Content";
    }

    //user måste vara inloggat och minst en user roll
    @PreAuthorize("hasRole('USER') or  hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/user")
    public String userAccess(){
        return "User content!";
    }

    //mod måste vara minst mod roll ej User
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/mod")
    public String modAcces(){
        return "MODERATOR CONTENT!";
    }

    // ONLY ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAcces(){
        return "Admin content";
    }
}

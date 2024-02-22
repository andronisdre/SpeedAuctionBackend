package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserServices userServices;


    // POST
    @PostMapping()
    public UserModels addUser(@RequestBody UserModels userModels) {
        return userServices.addUser(userModels);
    }

    // GET ALL
    @GetMapping("/all")
    public List<UserModels> getAllUsers() {
        return userServices.getAllUsers();
    }

    // GET BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModels getUserById(@PathVariable String id) {
        return userServices.getUserById(id);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserModels userDetails) {
        try {
            UserModels updatedUser = userServices.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        return userServices.deleteUser(id);
    }


  /* // POST
    @PostMapping("/favourite/add/{id}")
    public ResponseEntity<?> addFavouriteAuctions(@PathVariable String id, @RequestBody String auctionsId) {
        try {
            UserModels updatedUser = userServices.addFavouriteAuctions(id, auctionsId);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // DELETE
    @DeleteMapping("/favourite/remove/{id}")
    public ResponseEntity<?> deleteFavouriteAuctions(@PathVariable String id, @RequestBody String auctionsId) {
        try {
            UserModels updatedUser = userServices.deleteFavouriteAuctions(id, auctionsId);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

    // POST
    @PostMapping("/favourite/add/{id}")
    public ResponseEntity<?> addFavouriteAuctions(@PathVariable String id, @RequestBody String auctionId) {
        try {
            UserModels updatedUser = userServices.addFavouriteAuctions(id, auctionId);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/favourite/remove/{Id}")
    public ResponseEntity<?> deleteFavouriteAuctions(@PathVariable String userId, @PathVariable String auctionId) {
        try {
            UserModels updatedUser = userServices.deleteFavouriteAuctions(userId, auctionId);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

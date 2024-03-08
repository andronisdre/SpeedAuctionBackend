package com.spring.SpeedAuction.Controller;

import DTO.FavouriteDTO;
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

    // POST
    @PostMapping("/Add/favourite/{id}") // DENNA ÄR NY OTESTAD
    public ResponseEntity<UserModels> addFavourite(@PathVariable String id, @RequestBody FavouriteDTO favouriteDTO) {
        UserModels updatedUser = userServices.addFavourite(id, favouriteDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // GET ALL USERS WITH FAVOURITE AUCTIONS
    @GetMapping("/all/favourite")
    public ResponseEntity<List<UserModels>> getUsersWithFavouriteAuctions() {
        List<UserModels> usersWithFavouriteAuctions = userServices.getUsersWithFavouriteAuctions();
        return new ResponseEntity<>(usersWithFavouriteAuctions, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/favourite/delete/{id}/{auctionId}")
    public ResponseEntity<UserModels> deleteFavouriteAuctions(@PathVariable String id, @PathVariable String auctionId) {
        UserModels updatedUser = userServices.deleteFavouriteAuctions(id, auctionId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}

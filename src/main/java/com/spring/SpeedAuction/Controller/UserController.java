package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.dto.FavouriteDTO;
import com.spring.SpeedAuction.dto.UserResponsDTO;
import com.spring.SpeedAuction.security.Services.UserServices;
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
    @PostMapping("/Add/favourite/{id}")
    public ResponseEntity<UserModels> addFavourite(@PathVariable String id, @RequestBody FavouriteDTO favouriteDTO) {
        UserModels updatedUser = userServices.addFavourite(id, favouriteDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // GET ALL
    @GetMapping("/all/favourite")
    public ResponseEntity<List<UserResponsDTO>> getUsersWithFavouriteAuction() {
        List<UserResponsDTO> favourite = userServices.getUsersWithFavouriteAuction();

        return new ResponseEntity<>(favourite, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/favourite/delete/{id}/{auctionId}")
    public ResponseEntity<UserModels> deleteFavouriteAuctions(@PathVariable String id, @PathVariable String auctionId) {
        UserModels updatedUser = userServices.deleteFavouriteAuctions(id, auctionId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.DTO.FavouriteDTO;
import com.spring.SpeedAuction.DTO.UserResponsDTO;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Security.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    // GET ALL
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all")
    public List<UserModels> getAllUsers() {
        return userServices.getAllUsers();
    }

    // GET BY ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModels getUserById(@PathVariable String id) {
        return userServices.getUserById(id);
    }

    // PUT
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        return userServices.deleteUser(id);
    }

    // POST
    @PreAuthorize("hasRole('User') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/Add/favourite/{id}")
    public ResponseEntity<UserModels> addFavourite(@PathVariable String id, @RequestBody FavouriteDTO favouriteDTO) {
        UserModels updatedUser = userServices.addFavourite(id, favouriteDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // GET ALL
    @PreAuthorize("hasRole('User') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all/favourite")
    public ResponseEntity<List<UserResponsDTO>> getUsersWithFavouriteAuction() {
        List<UserResponsDTO> favourite = userServices.getUsersWithFavouriteAuction();

        return new ResponseEntity<>(favourite, HttpStatus.OK);
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @DeleteMapping("/favourite/delete/{id}/{auctionId}")
    public ResponseEntity<UserModels> deleteFavouriteAuctions(@PathVariable String id, @PathVariable String auctionId) {
        UserModels updatedUser = userServices.deleteFavouriteAuctions(id, auctionId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

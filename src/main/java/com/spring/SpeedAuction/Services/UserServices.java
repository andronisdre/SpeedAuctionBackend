package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;


    public UserModels addUser(UserModels user) {    // POST Registrera ny användare
        return userRepository.save(user);
    }

    public List<UserModels> getAllUsers() {     // GET Hämta en lista över alla användare
        return userRepository.findAll();
    }

    public UserModels getUserById(String id) {  // GET Hämta en specefik användares information
        return userRepository.findById(id).get();
    }

    public UserModels updateUser(String id, UserModels updatedUser) {       // Put Updatera en användares information
        return userRepository.findById(id)
                .map(existingUserModels -> {
                    if (updatedUser.getUsername() != null) {
                        existingUserModels.setUsername(updatedUser.getUsername());
                    }
                    if (updatedUser.getCity() != null) {
                        existingUserModels.setCity(updatedUser.getCity());
                    }
                    if (updatedUser.getAddress() != null) {
                        existingUserModels.setAddress(updatedUser.getAddress());
                    }
                    if (updatedUser.getCountry() != null) {
                        existingUserModels.setCountry(updatedUser.getCountry());
                    }
                    if (updatedUser.getEmail() != null) {
                        existingUserModels.setEmail(updatedUser.getEmail());
                    }
                    if (updatedUser.getFirst_name() != null) {
                        existingUserModels.setFirst_name(updatedUser.getFirst_name());
                    }
                    if (updatedUser.getLast_name() != null) {
                        existingUserModels.setLast_name(updatedUser.getLast_name());
                    }
                    if (updatedUser.getPassword() != null) {
                        existingUserModels.setPassword(updatedUser.getPassword());
                    }
                    if (updatedUser.getPhone_number() != null) {
                        existingUserModels.setPhone_number(updatedUser.getPhone_number());
                    }
                    if (updatedUser.getPostal_code() != null) {
                        existingUserModels.setPostal_code(updatedUser.getPostal_code());
                    }
                    return userRepository.save(existingUserModels);
                        })
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

    }

   /* public UserModels updateUser (UserModels userModels) {   // Put Updatera en användares information
        return userRepository.save(userModels);
    }*/

    public String deleteUser(String id) {        // DELETE Ta bort en användare
        userRepository.deleteById(id);
        return "User is deleted";
    }


}

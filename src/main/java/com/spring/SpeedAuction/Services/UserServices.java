package com.spring.SpeedAuction.Services;

import DTO.FavouriteDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuctionRepository auctionRepository;


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
                    if (updatedUser.getFavourites_auction_id() != null) {  // Denna är för att uppdatera/lägga till en favorit auktion
                        List<AuctionModels> Favourites = existingUserModels.getFavourites_auction_id();
                        List<AuctionModels> updatedFavourites = updatedUser.getFavourites_auction_id();
                        Favourites.addAll(updatedFavourites);
                        existingUserModels.setFavourites_auction_id(Favourites);
                    }
                    return userRepository.save(existingUserModels);
                        })
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    public UserModels addFavourite(String id, FavouriteDTO favouriteDTO) { // HAR INTE TESTAT DEN KANSKE FUNKAR
        UserModels user = userRepository.findById(id) // DENNA ÄR NY OTESTAT
                .orElseThrow(() -> new NoSuchElementException("User id not found"));

        List<String> auctionIds = favouriteDTO.getFavouriteAutcktion();
        for (String auctionId : auctionIds) {
            AuctionModels auction = auctionRepository.findById(auctionId)
                    .orElseThrow(() -> new NoSuchElementException("Auction id not found"));
            user.getFavourites_auction_id().add(auction);
        }
        return userRepository.save(user);
    }

    public String deleteUser(String id) {        // DELETE Ta bort en användare
        userRepository.deleteById(id);
        return "User is deleted";
    }

    public List<UserModels> getUsersWithFavouriteAuctions() { // GET Hämta alla änvändare med favoritAuctions
        List<UserModels> users = userRepository.findAll();
        List<UserModels> usersWithFavouriteAuctions = new ArrayList<>();

        for (UserModels user : users) {
            List<AuctionModels> favouriteAuctions = user.getFavourites_auction_id();
            if (favouriteAuctions != null && !favouriteAuctions.isEmpty()) {
                usersWithFavouriteAuctions.add(user);
            }
        }
        return usersWithFavouriteAuctions;
    }

    public UserModels deleteFavouriteAuctions(String id, String auctionId) { // DELETE Ta bort favorit auktioner
        UserModels user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setFavourites_auction_id(
                user.getFavourites_auction_id().stream()
                        .filter(auction -> !auction.getId().equals(auctionId))
                        .collect(Collectors.toList())
        );
        return userRepository.save(user);
    }

    // HJÄLPMETOD
    private FavouriteDTO convertToDTO(UserModels userModels) {
        FavouriteDTO favouriteDTO = new FavouriteDTO();

        favouriteDTO.setFavouriteAutcktion(userModels.getFavourites_auction_id().stream().map(AuctionModels::getId).collect(Collectors.toList()));

        return favouriteDTO;
    }

}

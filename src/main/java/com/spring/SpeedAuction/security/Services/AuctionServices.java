package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.AuctionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServices {
    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    //post
    public AuctionModels createAuctionModels(AuctionsDTO auctionsDTO) {
        UserModels user = checkUserId(auctionsDTO);
        AuctionModels newAuction = retrieveData(auctionsDTO, user);
        user.setPassword(null);
        newAuction.setActive(false);
        return auctionRepository.save(newAuction);
    }

    //get all
    public List<AuctionsDTO> getAllAuctionModels() {
        List<AuctionModels> auctionModels = auctionRepository.findAll();
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get auction by id
    public AuctionModels getAuctionModelsById(String id) {
        return auctionRepository.findById(id).get();
    }

    //update
    public AuctionModels updateAuctionModels(String id, AuctionModels auctionModels) {
        AuctionModels existingAuction = auctionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));

        //update field
        if (auctionModels.getStartingBid() != 0){
            existingAuction.setStartingBid(auctionModels.getStartingBid());
        }
        if (auctionModels.getEndOfAuction() != null){
            auctionModels.setEndOfAuction(auctionModels.getEndOfAuction());
        }
        if (auctionModels.getUpdated_at() != null){
            auctionModels.setUpdated_at(auctionModels.getUpdated_at());
        }
        if (auctionModels.isActive()){
            auctionModels.setActive(auctionModels.isActive());
        }
        if (auctionModels.getCreated_at() != null){
            auctionModels.setCreated_at(auctionModels.getCreated_at());
        }

        existingAuction.setId(id);
        existingAuction.setSeller(existingAuction.getSeller());
        existingAuction.setStartingBid(existingAuction.getStartingBid());
        existingAuction.setCreated_at(existingAuction.getCreated_at());

        return auctionRepository.save(existingAuction);
    }

    //delete auction
    public String deleteAuctionModels(String id) {
        AuctionModels auctionModels = auctionRepository.findById(id).orElse(null);
        if (auctionModels != null) {
            auctionRepository.deleteById(id);
            return "auction deleted";
        }
        else {
            return "auction id doesnt exist";
        }
    }

    //get all auctions that are either active or inactive
    public List<AuctionsDTO> getAuctionModelsByIsActive(boolean isActive) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByIsActive(isActive);
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for that value");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions in the range between minStartingBid and maxStartingBid
    public List<AuctionsDTO> getAuctionModelsByStartingBidBetween(int minStartingBid, int maxStartingBid) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByStartingBidBetweenOrderByStartingBidAsc(minStartingBid, maxStartingBid);
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for those values");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // UTIL functions

    public UserModels checkUserId(AuctionsDTO auctionsDTO) {
        return userRepository.findById(auctionsDTO.getSellerId()).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    private AuctionsDTO convertToDTO(AuctionModels auctionModels) {
        AuctionsDTO auctionsDTO = new AuctionsDTO();
        auctionsDTO.setSellerId(auctionModels.getSeller().getId());
        auctionsDTO.setId(auctionModels.getId());
        auctionsDTO.setActive(auctionModels.isActive());
        auctionsDTO.setStartingBid(auctionModels.getStartingBid());
        auctionsDTO.setEndOfAuction(auctionModels.getEndOfAuction());
        auctionsDTO.setCreated_at(auctionModels.getCreated_at());

        return auctionsDTO;
    }

    public AuctionModels retrieveData(AuctionsDTO auctionsDTO, UserModels user) {
        AuctionModels newAuction = new AuctionModels();
        newAuction.setSeller(user);
        newAuction.setActive(auctionsDTO.isActive());
        newAuction.setStartingBid(auctionsDTO.getStartingBid());
        newAuction.setEndOfAuction(auctionsDTO.getEndOfAuction());
        newAuction.setCreated_at(auctionsDTO.getCreated_at());

        return newAuction;
    }
}

package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsModelsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsModelsService {
    //logic
    @Autowired
    BidsModelsRepository bidsModelsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;

    public BidsModels checkIds(BidsDTO bidsDTO) {
        UserModels user = userRepository.findById(bidsDTO.getBidderId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Invalid userId");
        }

        AuctionModels auction = auctionRepository.findById(bidsDTO.getAuctionId()).orElse(null);
        if (auction == null) {
            throw new IllegalArgumentException("Invalid auctionId");
        }

        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        newBid.setAuction(auction);
        newBid.setTimeBidded(bidsDTO.getTimeBidded());
        newBid.setAmount(bidsDTO.getAmount());
        newBid.setPriority(bidsDTO.getPriority());

        return bidsModelsRepository.save(newBid);
    }

    private BidsDTO convertToDTO(BidsModels bidsModels) {
        BidsDTO bidsDTO = new BidsDTO();
        bidsDTO.setBidderId(bidsModels.getBidder().getId());
        bidsDTO.setAuctionId(bidsModels.getAuction().getId());
        bidsDTO.setAmount(bidsModels.getAmount());
        bidsDTO.setTimeBidded(bidsModels.getTimeBidded());
        bidsDTO.setPriority(bidsModels.getPriority());

        return bidsDTO;
    }

    // Alla PostMan Funktioner

    // GET all
    public List<BidsDTO> getAllBidsModel() {
        List<BidsModels> bidsModels = bidsModelsRepository.findAll();
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET by id
    public BidsModels getBidsModel(String id) {
        return bidsModelsRepository.findById(id).get();
    }



    // POST
    public BidsModels createBidModels(BidsDTO bidsDTO) {
        // Kontroll om Auction ID finns

        // Kontroll om User ID Finns

        return checkIds(bidsDTO);
    }

    // PUT
    public BidsModels updateBidModels(String id, BidsModels updatedBidModel) {
        BidsModels existingBidsModel = bidsModelsRepository.findById(id).get();

        // Kontroll om Auction ID finns

        // Kontroll om User ID Finns

        //update field
        if (updatedBidModel.getAuction() != null) {
            existingBidsModel.setAuction(updatedBidModel.getAuction());
        }
        if (updatedBidModel.getBidder() != null) {
            existingBidsModel.setBidder(updatedBidModel.getBidder());
        }
        if (updatedBidModel.getAmount() != null){
            existingBidsModel.setAmount(updatedBidModel.getAmount());
        }
        if (updatedBidModel.getTimeBidded() != null){
            existingBidsModel.setTimeBidded(updatedBidModel.getTimeBidded());
        }
        if (updatedBidModel.getPriority() != null){
            existingBidsModel.setPriority(updatedBidModel.getPriority());
        }
        return bidsModelsRepository.save(existingBidsModel);
    }
    // DELETE

    public String deleteBidmodels(String id){
        bidsModelsRepository.deleteById(id);
        return "Bid deleted";
    }

    //bid history for a user
    //find all by userId
    public List<BidsModels> getBidsModelByUserId(UserModels bidderId) {
        return bidsModelsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
    }

    //bid history for an auction
    //find all by auctionId
    public List<BidsModels> getBidsModelByAuctionId(AuctionModels auctionId) {
        return bidsModelsRepository.findBidsModelsByAuctionIdOrderByTimeBiddedDesc(auctionId);
    }

}






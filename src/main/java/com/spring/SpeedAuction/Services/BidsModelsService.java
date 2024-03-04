package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsModelsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidsModelsService {
    //logic
    @Autowired
    BidsModelsRepository bidsModelsRepository;
    AuctionRepository auctionRepository;
    UserRepository userRepository;

    // Alla PostMan Funktioner

    // GET
    public List<BidsModels> getAllBidsModel() {
        return bidsModelsRepository.findAll();
    }

    public BidsModels getBidsModel(String id) {
        return bidsModelsRepository.findById(id).get();
    }



    // POST
    public BidsModels createBidModels(BidsModels bidsModel) {
        // Kontroll om Auction ID finns

        // Kontroll om User ID Finns

        return bidsModelsRepository.save(bidsModel);
    }

    // PUT
    public BidsModels updateBidModels(String id, BidsModels updatedBidModel) {
        BidsModels existingBidsModel = bidsModelsRepository.findById(id).get();

        // Kontroll om Auction ID finns

        // Kontroll om User ID Finns

        //update field
        if (updatedBidModel.getAuctionId() != null) {
            existingBidsModel.setAuctionId(updatedBidModel.getAuctionId());
        }
        if (updatedBidModel.getBidderId() != null) {
            existingBidsModel.setBidderId(updatedBidModel.getBidderId());
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
    public List<BidsModels> getBidsModelByUserId(String bidderId) {
        return bidsModelsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
    }

    //bid history for an auction
    //find all by auctionId
    public List<BidsModels> getBidsModelByAuctionId(String auctionId) {
        return bidsModelsRepository.findBidsModelsByAuctionIdOrderByTimeBiddedDesc(auctionId);
    }

}






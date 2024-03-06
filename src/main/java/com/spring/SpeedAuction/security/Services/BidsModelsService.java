package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsModelsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (updatedBidModel.getAuction_id() != null) {
            existingBidsModel.setAuction_id(updatedBidModel.getAuction_id());
        }
        if (updatedBidModel.getBidder_id() != null) {
            existingBidsModel.setBidder_id(updatedBidModel.getBidder_id());
        }
        if (updatedBidModel.getAmount() != null){
            existingBidsModel.setAmount(updatedBidModel.getAmount());
        }
        if (updatedBidModel.getTime() != null){
            existingBidsModel.setTime(updatedBidModel.getTime());
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

}






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

    public UserModels checkUserId(BidsDTO bidsDTO) {
        return userRepository.findById(bidsDTO.getBidderId()).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    public AuctionModels checkAuctionId(BidsDTO bidsDTO) {
        return auctionRepository.findById(bidsDTO.getAuctionId()).orElseThrow(() -> new IllegalArgumentException("Invalid auctionId"));
    }

    public BidsModels retrieveData(BidsDTO bidsDTO, AuctionModels auction, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        newBid.setAuction(auction);
        newBid.setTimeBidded(bidsDTO.getTimeBidded());
        newBid.setAmount(bidsDTO.getAmount());
        newBid.setPriority(bidsDTO.getPriority());

        return bidLogic(bidsDTO, newBid, auction);
    }

    public BidsModels bidLogic(BidsDTO bidsDTO, BidsModels newBid, AuctionModels auction) {

        if (newBid.getAmount() < auction.getStartingBid()) {
            throw new IllegalArgumentException("bid is smaller than Starting bid!");
        }

        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByAuctionIdOrderByTimeBiddedDesc(bidsDTO.getAuctionId());
        for (BidsModels existingBid : bidsModels) {
            if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
            }
        }

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

        UserModels user = checkUserId(bidsDTO);
        AuctionModels auctions = checkAuctionId(bidsDTO);

        return retrieveData(bidsDTO, auctions, user);
    }

    // PUT
    public BidsModels updateBidModels(BidsModels updatedBidModel, BidsDTO bidsDTO) {
        BidsModels existingBidsModel = bidsModelsRepository.findById(updatedBidModel.getId()).orElseThrow(() -> new IllegalArgumentException("invalid id"));

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
        checkUserId(bidsDTO);
        checkAuctionId(bidsDTO);
        return bidsModelsRepository.save(existingBidsModel);
    }
    // DELETE

    public String deleteBidModels(String id){
        BidsModels bidsModels = bidsModelsRepository.findById(id).orElse(null);
        if (bidsModels != null) {
            bidsModelsRepository.deleteById(id);
            return "bid deleted";
        } else {
            return "bid id doesn't exist";
        }
    }

    //bid history for a user
    //find all by bidderId
    public List<BidsDTO> getBidsModelByUserId(String bidderId) {
        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //bid history for an auction
    //find all by auctionId
    public List<BidsDTO> getBidsModelByAuctionId(String auctionId) {
        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByAuctionIdOrderByTimeBiddedDesc(auctionId);
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

}






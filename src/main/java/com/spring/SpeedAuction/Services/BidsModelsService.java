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

    //util functions
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

        return newBid;
    }

    public void bidLargeEnough(BidsDTO bidsDTO, BidsModels newBid, AuctionModels auction) {

        if (newBid.getAmount() < auction.getStartingBid()) {
            throw new IllegalArgumentException("bid is smaller than Starting bid!");
        }

        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(bidsDTO.getAuctionId());
        for (BidsModels existingBid : bidsModels) {
            if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
            }
        }
    }

    private BidsDTO convertToDTO(BidsModels bidsModels) {
        BidsDTO bidsDTO = new BidsDTO();
        bidsDTO.setBidderId(bidsModels.getBidder().getId());
        bidsDTO.setAuctionId(bidsModels.getAuction().getId());
        bidsDTO.setAmount(bidsModels.getAmount());
        bidsDTO.setTimeBidded(bidsModels.getTimeBidded());

        return bidsDTO;
    }
    //util functions end



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
        UserModels user = checkUserId(bidsDTO);
        AuctionModels auction = checkAuctionId(bidsDTO);
        BidsModels newBid = retrieveData(bidsDTO, auction, user);
        bidLargeEnough(bidsDTO, newBid, auction);
        return bidsModelsRepository.save(newBid);
    }

    // PUT
    public BidsModels updateBidModels(String id, BidsModels bidsModels) {
        BidsModels existingBid = bidsModelsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));

        //update field
        if (bidsModels.getAmount() != null){
            existingBid.setAmount(bidsModels.getAmount());
        }
        if (bidsModels.getTimeBidded() != null){
            existingBid.setTimeBidded(bidsModels.getTimeBidded());
        }
        existingBid.setId(id);
        existingBid.setBidder(existingBid.getBidder());
        existingBid.setAuction(existingBid.getAuction());
        BidsDTO bidsDTO = convertToDTO(existingBid);
        checkUserId(bidsDTO);
        AuctionModels auction = checkAuctionId(bidsDTO);
        bidLargeEnough(bidsDTO, existingBid, auction);

        return bidsModelsRepository.save(existingBid);
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
        if (bidsModels.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this userId");
        }
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //bid history for an auction
    //find all by auctionId
    public List<BidsDTO> getBidsModelByAuctionId(String auctionId) {
        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(auctionId);
        if (bidsModels.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //GET top bid for an auction
    public BidsDTO getTopBidByAuctionId(String auctionId) {
        BidsModels bidsModel = bidsModelsRepository.findFirstByAuctionIdOrderByAmountDesc(auctionId);
        if (bidsModel == null) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }
        return convertToDTO(bidsModel);
    }

}






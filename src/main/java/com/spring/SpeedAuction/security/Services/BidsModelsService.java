package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsModelsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public UserModels checkUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    public AuctionModels checkAuctionId(BidsDTO bidsDTO) {
        return auctionRepository.findById(bidsDTO.getAuctionId()).orElseThrow(() -> new IllegalArgumentException("Invalid auctionId"));
    }

    public void checkIsActive(AuctionModels auction) {
        if (!auction.isActive()) {
            throw new IllegalArgumentException("you cant bid on an inactive auction");
        }
    }

    public void compareSellerAndBidder(AuctionModels auction, BidsModels newBid) {
        if (auction.getSeller().getId().equals(newBid.getBidder().getId())) {
            throw new IllegalArgumentException("you cant bid on your own auction");
        }
    }

    public BidsModels retrieveData(BidsDTO bidsDTO, AuctionModels auction, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        newBid.setAuction(auction);
        newBid.setTimeBidded(bidsDTO.getTimeBidded());
        newBid.setAmount(bidsDTO.getAmount());

        return newBid;
    }

    public void bidBeforeEndOfAuction(BidsModels newBid, AuctionModels auctionModels) {
        if(auctionModels.getEndOfAuction().before(newBid.getTimeBidded())) {
            throw new IllegalArgumentException("This auction has already ended");
        }
    }

    public void bidLargeEnough(BidsDTO bidsDTO, BidsModels newBid, AuctionModels auction) {

        // ###### BRYTA NER TILL TVÅ OLIKA METODER
        if (newBid.getAmount() < auction.getStartingPrice()) {
            throw new IllegalArgumentException("bid is smaller than Starting bid!");
        }

        List<BidsModels> bidsModels = bidsModelsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(bidsDTO.getAuctionId());
        for (BidsModels existingBid : bidsModels) {
            if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
            }
        }
    } // #######

    private BidsDTO convertToDTO(BidsModels bidsModels) {
        BidsDTO bidsDTO = new BidsDTO();
        bidsDTO.setBidderId(bidsModels.getBidder().getId());
        bidsDTO.setAuctionId(bidsModels.getAuction().getId());
        bidsDTO.setAmount(bidsModels.getAmount());
        bidsDTO.setTimeBidded(bidsModels.getTimeBidded());

        return bidsDTO;
    }

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
    public BidsModels createBidModels(String auctionId, String userId, BidsDTO bidsDTO) {
        UserModels user = checkUserId(userId);
        bidsDTO.setAuctionId(auctionId);
        AuctionModels auction = checkAuctionId(bidsDTO);
        BidsModels newBid = retrieveData(bidsDTO, auction, user);
        checkIsActive(auction);
        compareSellerAndBidder(auction, newBid);
        bidLargeEnough(bidsDTO, newBid, auction);
        newBid.setTimeBidded(new Date());
        bidBeforeEndOfAuction(newBid, auction);
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
        checkUserId(bidsDTO.getBidderId());
        AuctionModels auction = checkAuctionId(bidsDTO);
        checkIsActive(auction);
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






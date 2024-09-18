package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
<<<<<<< HEAD
=======
import com.spring.SpeedAuction.DTO.BidsDTO;
>>>>>>> OOAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsServices {

    //logic
    @Autowired
    BidsRepository bidsModelsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;

    //util functions
    public UserModels checkUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    public AuctionModels checkAuctionId(String auctionId) {
        return auctionRepository.findById(auctionId).orElseThrow(() -> new IllegalArgumentException("Invalid auctionId"));
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

    public BidsModels retrieveData(BidsDTO bidsDTO, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        newBid.setTimeBidded(bidsDTO.getTimeBidded());
        newBid.setAmount(bidsDTO.getAmount());

        return newBid;
    }

    public void bidBeforeEndOfAuction(BidsModels newBid, AuctionModels auctionModels) {
        if(auctionModels.getEndOfAuction().before(newBid.getTimeBidded())) {
            throw new IllegalArgumentException("This auction has already ended");
        }
    }

    public void bidLargeEnough(BidsModels newBid, AuctionModels auction) {

        // ###### BRYTA NER TILL TVÅ OLIKA METODER
        if (newBid.getAmount() < auction.getStartingPrice()) {
            throw new IllegalArgumentException("bid is smaller than Starting bid!");
        }

        List<BidsModels> bidsModels = auction.getBids();
        for (BidsModels existingBid : bidsModels) {
            if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
            }
        }
    }

    //saves both bid and auction then returns bid
    public BidsModels saveBidAndAuction(AuctionModels auction, BidsModels bid) {
        List<BidsModels> bids = auction.getBids();
        bids.add(bid);
        auction.setBids(bids);

        bidsModelsRepository.save(bid);

        auctionRepository.save(auction);

        return bid;
    }

    private BidsDTO convertToDTO(BidsModels bidsModels) {
        BidsDTO bidsDTO = new BidsDTO();
        bidsDTO.setBidderId(bidsModels.getBidder().getId());
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
        AuctionModels auction = checkAuctionId(auctionId);
        checkIsActive(auction);
        BidsModels newBid = retrieveData(bidsDTO, user);
        compareSellerAndBidder(auction, newBid);
        bidLargeEnough (newBid, auction);
        newBid.setTimeBidded(new Date());
        bidBeforeEndOfAuction(newBid, auction);

        return saveBidAndAuction(auction, newBid);
    }

    // PUT
    public BidsModels updateBidModels(String id, String auctionId, BidsModels bidsModels) {
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
        BidsDTO bidsDTO = convertToDTO(existingBid);
        checkUserId(bidsDTO.getBidderId());
        AuctionModels auction = checkAuctionId(auctionId);
        checkIsActive(auction);
        bidLargeEnough(existingBid, auction);

        return saveBidAndAuction(auction, existingBid);
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
        checkAuctionId(auctionId);
        List<BidsModels> bids = checkAuctionId(auctionId).getBids();
        if (bids == null || bids.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }
        return bids.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //GET top bid for an auction
    public BidsDTO getTopBidByAuctionId(String auctionId) {
        checkAuctionId(auctionId);
        List<BidsModels> bids = checkAuctionId(auctionId).getBids();
        if (bids == null || bids.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }

        BidsModels biggestBid = bids.get(0);

        for (BidsModels bid : bids) {
            if (bid.getAmount() > biggestBid.getAmount()) {
                biggestBid = bid;
            }
        }

        return convertToDTO(biggestBid);
    }
}






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
    @Autowired
    BidsValidateService bidsValidateService;
    @Autowired
    BidsDataService bidsDataService;


    public BidsModels retrieveData(BidsDTO bidsDTO, AuctionModels auction, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        newBid.setAuction(auction);
        newBid.setTimeBidded(bidsDTO.getTimeBidded());
        newBid.setAmount(bidsDTO.getAmount());

        return newBid;
    }


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

    public BidsModels createBidModels(String auctionId, String userId, BidsDTO bidsDTO) {
        UserModels user = bidsValidateService.checkUserId(userId);
        AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
        bidsValidateService.checkIsActive(auction);
        bidsValidateService.compareSellerAndBidder(auction, user);

        BidsModels newBid = bidsCreationService.createNewBid(bidsDTO, auction, user);
        return bidsDataService.saveBid(newBid);
    }

    public BidsModels updateBidModels(String id, BidsModels bidsModels) {
        BidsModels existingBid = bidsDataService.getBidById(id);
        if (bidsModels.getAmount() != null) {
            existingBid.setAmount(bidsModels.getAmount());
        }
        if (bidsModels.getTimeBidded() != null) {
            existingBid.setTimeBidded(bidsModels.getTimeBidded());
        }
        return bidsDataService.saveBid(existingBid);
    }

}






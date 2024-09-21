package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsServices {

    private final AuctionRepository auctionRepository;
    private final BidsRepository bidsRepository;
    private final BidsValidateService bidsValidateService;

    public BidsServices(AuctionRepository auctionRepository,
                        BidsRepository bidsRepository, BidsValidateService bidsValidateService) {
        this.auctionRepository = auctionRepository;
        this.bidsRepository = bidsRepository;
        this.bidsValidateService = bidsValidateService;
    }

    public BidsModels retrieveData(BidsDTO bidsDTO, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        if (bidsDTO.getTimeBidded() != null) {
            newBid.setTimeBidded(bidsDTO.getTimeBidded());
        } else {
            newBid.setTimeBidded(new Date());
        }
        newBid.setAmount(bidsDTO.getAmount());

        return newBid;
    }

    //saves both bid and auction then returns bid
    public BidsModels saveBidAndAuction(AuctionModels auction, BidsModels bid) {
        List<BidsModels> existingBids = auction.getBids();
        if (existingBids == null) {
            existingBids = (new ArrayList<>());
        }
        existingBids.add(bid);
        auction.setBids(existingBids);

        bidsRepository.save(bid);

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
        List<BidsModels> bidsModels = bidsRepository.findAll();
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET by id
    public BidsModels getBidsModel(String id) {
        return bidsRepository.findById(id).get();
    }

    // POST
    public BidsModels createBidModels(String auctionId, String userId, BidsDTO bidsDTO) {
        UserModels user = bidsValidateService.checkUserId(userId);
        AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
        bidsValidateService.checkIsActive(auction);

        BidsModels newBid = retrieveData(bidsDTO, user);
        bidsValidateService.compareSellerAndBidder(auction, newBid);
        bidsValidateService.bidLargeEnough(newBid, auction);
        bidsValidateService.bidBeforeEndOfAuction(newBid, auction);

        return saveBidAndAuction(auction, newBid);
    }

    // PUT
    public BidsModels updateBidModels(String id, String auctionId, BidsModels bidsModels) {

        BidsModels existingBid = bidsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid ID"));


        AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
        bidsValidateService.checkIsActive(auction);

        if (bidsModels.getAmount() != null) {
            existingBid.setAmount(bidsModels.getAmount());
        }
        if (bidsModels.getTimeBidded() != null) {
            existingBid.setTimeBidded(bidsModels.getTimeBidded());
        }

        bidsValidateService.bidLargeEnough(existingBid, auction);

        return bidsRepository.save(existingBid);
    }

    // DELETE
    public String deleteBidModels(String id) {

            BidsModels bidsModels = bidsRepository.findById(id).orElse(null);
            if (bidsModels != null) {
                bidsRepository.deleteById(id);
                return "bid deleted";
            } else {
                return "bid id doesn't exist";
            }
        }

        //bid history for a user
        //find all by bidderId
        public List<BidsDTO> getBidsModelByUserId (String bidderId){
            List<BidsModels> bidsModels = bidsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
            if (bidsModels.isEmpty()) {
                throw new IllegalArgumentException("no bids exist for this userId");
            }
            return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        //bid history for an auction
        //find all by auctionI
        public List<BidsDTO> getBidsModelByAuctionId (String auctionId){
            AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
            List<BidsModels> bids = auction.getBids();
            if (bids == null || bids.isEmpty()) {
                throw new IllegalArgumentException("No bids exist for this auction ID");
            }
            return bids.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        //GET top bid for an auction

        public BidsDTO getTopBidByAuctionId (String auctionId){
            AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
            List<BidsModels> bids = auction.getBids();
            if (bids == null || bids.isEmpty()) {
                throw new IllegalArgumentException("No bids exist for this auction ID");
            }

            BidsModels topBid = bids.get(0);
            for (BidsModels bid : bids) {
                if (bid.getAmount() > topBid.getAmount()) {
                    topBid = bid;
                }
            }
            return convertToDTO(topBid);
        }
    }
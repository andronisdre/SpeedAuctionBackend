package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.BidsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsServices {


    private final AutoBidServices autoBidServices;
    private final BidsRepository bidsRepository;
    private final BidsValidateService bidsValidateService;

    public BidsServices(AutoBidServices autoBidServices, BidsRepository bidsRepository,
                        BidsValidateService bidsValidateService) {
        this.autoBidServices = autoBidServices;
        this.bidsRepository = bidsRepository;
        this.bidsValidateService = bidsValidateService;
    }

    // GET all
    public List<BidsDTO> getAllBidsModel() {
        List<BidsModels> bidsModels = bidsRepository.findAll();
        return bidsModels.stream().map(bidsValidateService::convertToDTO).collect(Collectors.toList());
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

        BidsModels newBid = bidsValidateService.retrieveData(bidsDTO, user);
        bidsValidateService.compareSellerAndBidder(auction, newBid);
        bidsValidateService.bidLargeEnough(newBid, auction);
        bidsValidateService.bidBeforeEndOfAuction(newBid, auction);
        auction = bidsValidateService.setAuctionBids(auction, newBid);
        bidsValidateService.saveBidAndAuction(auction, newBid);

        autoBidServices.checkForAutobids(auction);

        return newBid;
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
            return bidsModels.stream().map(bidsValidateService::convertToDTO).collect(Collectors.toList());
        }

        //bid history for an auction
        //find all by auctionI
        public List<BidsDTO> getBidsModelByAuctionId (String auctionId){
            AuctionModels auction = bidsValidateService.checkAuctionId(auctionId);
            List<BidsModels> bids = auction.getBids();
            if (bids == null || bids.isEmpty()) {
                throw new IllegalArgumentException("No bids exist for this auction ID");
            }
            return bids.stream().map(bidsValidateService::convertToDTO).collect(Collectors.toList());
        }
}
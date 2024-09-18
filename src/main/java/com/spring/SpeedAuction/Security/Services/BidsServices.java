package com.spring.SpeedAuction.Security.Services;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsServices {

    //logic
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BidsValidateService bidsValidateService;


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
        bidsDTO.setAuctionId(auctionId);
        AuctionModels auction = bidsValidateService.checkAuctionId(bidsDTO);
        BidsModels newBid = retrieveData(bidsDTO, auction, user);
        bidsValidateService.checkIsActive(auction);
        bidsValidateService.compareSellerAndBidder(auction, newBid);
        bidsValidateService.bidLargeEnough(bidsDTO, newBid, auction);
        newBid.setTimeBidded(new Date());
        bidsValidateService.bidBeforeEndOfAuction(newBid, auction);
        return bidsRepository.save(newBid);
    }


    // PUT
    public BidsModels updateBidModels(String id, BidsModels bidsModels) {
        BidsModels existingBid = bidsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));

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
        bidsValidateService.checkUserId(bidsDTO.getBidderId());
        AuctionModels auction = bidsValidateService.checkAuctionId(bidsDTO);
        bidsValidateService.checkIsActive(auction);
        bidsValidateService.bidLargeEnough(bidsDTO, existingBid, auction);

        return bidsRepository.save(existingBid);
    }

    // DELETE
    public String deleteBidModels(String id){
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
    public List<BidsDTO> getBidsModelByUserId(String bidderId) {
        List<BidsModels> bidsModels = bidsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
        if (bidsModels.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this userId");
        }
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //bid history for an auction
    //find all by auctionId
    public List<BidsDTO> getBidsModelByAuctionId(String auctionId) {
        List<BidsModels> bidsModels = bidsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(auctionId);
        if (bidsModels.isEmpty()) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }
        return bidsModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //GET top bid for an auction
    public BidsDTO getTopBidByAuctionId(String auctionId) {
        BidsModels bidsModel = bidsRepository.findFirstByAuctionIdOrderByAmountDesc(auctionId);
        if (bidsModel == null) {
            throw new IllegalArgumentException("no bids exist for this auctionId");
        }
        return convertToDTO(bidsModel);
    }


}






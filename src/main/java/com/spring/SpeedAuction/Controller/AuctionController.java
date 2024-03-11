package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Services.AuctionServices;
import com.spring.SpeedAuction.dto.AuctionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/auctions")
public class AuctionController {
    @Autowired
    AuctionServices auctionServices;


    //POST
    @PostMapping()
    public AuctionModels createAuctionModels(@RequestBody AuctionsDTO auctionsDTO) {
        return auctionServices.createAuctionModels(auctionsDTO);
    }

    //GET ALL
    @GetMapping("/all")
    public List<AuctionsDTO> getAllAuctionModels() {
        return auctionServices.getAllAuctionModels();
    }

    //GET by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AuctionModels getAuctionModelsById(@PathVariable String id) {
        return auctionServices.getAuctionModelsById(id);
    }

    //PUT by id
    @PutMapping(value = "/{id}")
    public AuctionModels updateAuctionModels(@PathVariable String id, @RequestBody AuctionModels auctionModels) {
        return auctionServices.updateAuctionModels(id, auctionModels);
    }

    //DELETE by id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAuctionModels(@PathVariable String id) {
        return auctionServices.deleteAuctionModels(id);
    }

    //get FILTER by isActive
    @GetMapping("/filterByIsActive/{isActive}")
    public List<AuctionsDTO> getAuctionModelsByIsActive(@PathVariable boolean isActive) {
        return auctionServices.getAuctionModelsByIsActive(isActive);
    }

    //get FILTER by startingBid in a range
    @GetMapping("/filterByStartingBidBetween/{minStartingBid}/{maxStartingBid}")
    public List<AuctionsDTO> getAuctionModelsByStartingBidBetween(@PathVariable int minStartingBid, @PathVariable int maxStartingBid) {
        return auctionServices.getAuctionModelsByStartingBidBetween(minStartingBid, maxStartingBid);
    }
}


package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Services.AuctionServices;
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
    public AuctionModels createAuctionModels(@RequestBody AuctionModels auctionModels) {
        return auctionServices.createAuctionModels(auctionModels);
    }

    //GET ALL
    @GetMapping("/all")
    public List<AuctionModels> getAllAuctionModels() {
        return auctionServices.getAllAuctionModels();
    }

    //FILTER by yearManufactured in a range
    @GetMapping("/filterByYearManufacturedBetween/{minYearManufactured}/{maxYearManufactured}")
    public List<AuctionModels> getAuctionModelsByYearManufacturedBetween(@PathVariable int minYearManufactured, @PathVariable int maxYearManufactured) {
        return auctionServices.getAuctionModelsByYearManufacturedBetween(minYearManufactured, maxYearManufactured);
    }

    //FILTER by isActive
    @GetMapping("/filterByIsActive/{isActive}")
    public List<AuctionModels> getAuctionModelsByIsActive(@PathVariable boolean isActive) {
        return auctionServices.getAuctionModelsByIsActive(isActive);
    }

    //FILTER by startingBid in a range
    @GetMapping("/filterByStartingBidBetween/{minStartingBid}/{maxStartingBid}")
    public List<AuctionModels> getAuctionModelsByStartingBidBetween(@PathVariable int minStartingBid, @PathVariable int maxStartingBid) {
        return auctionServices.getAuctionModelsByStartingBidBetween(minStartingBid, maxStartingBid);
    }

    //FILTER by milesDriven in a range
    @GetMapping("/filterByMilesDrivenBetween/{minMilesDriven}/{maxMilesDriven}")
    public List<AuctionModels> getAuctionModelsByMilesDrivenBetween(@PathVariable int minMilesDriven, @PathVariable int maxMilesDriven) {
        return auctionServices.getAuctionModelsByMilesDrivenBetween(minMilesDriven, maxMilesDriven);
    }

    //FILTER by custom filter
    @GetMapping("/filterByCustomFilter/{minStartingBid}/{maxStartingBid}/{yearManufactured}/{isActive}")
    public List<AuctionModels> getAuctionModelsByCustomFilter(@PathVariable int minStartingBid, @PathVariable int maxStartingBid, @PathVariable int yearManufactured, @PathVariable boolean isActive) {
        return auctionServices.getAuctionModelsByCustomFilter(minStartingBid, maxStartingBid, yearManufactured, isActive);
    }

    //PUT by id
    @PutMapping(value = "/{id}")
    public AuctionModels updateAuctionModels(@RequestBody AuctionModels auctionModels) {
        return auctionServices.updateAuctionModels(auctionModels);
    }

    //GET by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AuctionModels getAuctionModelsById(@PathVariable String id) {
        return auctionServices.getAuctionModelsById(id);
    }

    //DELETE by id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAuctionModels(@PathVariable String id) {
        return auctionServices.deleteAuctionModels(id);
    }
}


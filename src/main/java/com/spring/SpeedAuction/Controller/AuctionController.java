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

    //get FILTER by yearManufactured in a range
    @GetMapping("/filterByYearManufacturedBetween/{minYearManufactured}/{maxYearManufactured}")
    public List<AuctionModels> getAuctionModelsByYearManufacturedBetween(@PathVariable int minYearManufactured, @PathVariable int maxYearManufactured) {
        return auctionServices.getAuctionModelsByYearManufacturedBetween(minYearManufactured, maxYearManufactured);
    }

    //get FILTER by brand
    @GetMapping("/filterByBrand/{brand}")
    public List<AuctionModels> getAuctionModelsByBrand(@PathVariable String brand) {
        return auctionServices.getAuctionModelsByBrand(brand);
    }

    //get FILTER by milesDriven in a range
    @GetMapping("/filterByMilesDrivenBetween/{minMilesDriven}/{maxMilesDriven}")
    public List<AuctionModels> getAuctionModelsByMilesDrivenBetween(@PathVariable int minMilesDriven, @PathVariable int maxMilesDriven) {
        return auctionServices.getAuctionModelsByMilesDrivenBetween(minMilesDriven, maxMilesDriven);
    }

    //get FILTER by custom filter
    @GetMapping("/filterByCustomFilter/{minStartingBid}/{maxStartingBid}/{minYearManufactured}/{maxYearManufactured}/{isActive}/{brand}/{minMilesDriven}/{maxMilesDriven}")
    public List<AuctionModels> getAuctionModelsByCustomFilter(
            @PathVariable int minStartingBid,
            @PathVariable int maxStartingBid,
            @PathVariable int minYearManufactured,
            @PathVariable int maxYearManufactured,
            @PathVariable boolean isActive,
            @PathVariable String brand,
            @PathVariable int minMilesDriven,
            @PathVariable int maxMilesDriven) {
        return auctionServices.getAuctionModelsByCustomFilter(minStartingBid, maxStartingBid, minYearManufactured, maxYearManufactured, isActive, brand, minMilesDriven, maxMilesDriven);
    }
}


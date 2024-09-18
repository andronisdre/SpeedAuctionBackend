package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.DTO.AuctionsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Security.Services.AuctionServices;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/api/auctions")
public class AuctionController {

    private final AuctionServices auctionServices;

    public AuctionController(AuctionServices auctionServices) {
        this.auctionServices = auctionServices;
    }

    //post auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/{userId}")
    public AuctionModels createAuctionModels(@PathVariable String userId, @Valid @RequestBody AuctionsDTO auctionsDTO) {
        return auctionServices.createAuctionModels(auctionsDTO, userId);
    }

    //GET ALL Auction
    @GetMapping("/all")
    public List<AuctionsDTO> getAllAuctionModels() {
        return auctionServices.getAllAuctionModels();
    }


    //GET a unique auction by it's id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AuctionModels getAuctionModelsById(@PathVariable String id) {
        return auctionServices.getAuctionModelsById(id);
    }

    //Change and update an auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping(value = "/{id}")
    public AuctionModels updateAuctionModels(@PathVariable String id, @RequestBody AuctionModels auctionModels) {
        return auctionServices.updateAuctionModels(id, auctionModels);
    }

    //Remove an auction
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAuctionModels(@PathVariable String id) {
        return auctionServices.deleteAuctionModels(id);
    }

    //get FILTER by isActive
    @GetMapping("/filterByIsActive/{isActive}")
    public List<AuctionsDTO> getAuctionModelsByIsActive(@PathVariable boolean isActive) {
        return auctionServices.getAuctionModelsByIsActive(isActive);
    }

    //get FILTER by startingPrice in a range
    @GetMapping("/filterByStartingPriceBetween/{minStartingPrice}/{maxStartingPrice}")
    public List<AuctionsDTO> getAuctionModelsByStartingPriceBetween(@PathVariable int minStartingPrice, @PathVariable int maxStartingPrice) {
        return auctionServices.getAuctionModelsByStartingPriceBetween(minStartingPrice, maxStartingPrice);
    }

    //get FILTER by color
    @GetMapping("/filterByColor/{color}")
    public List<AuctionsDTO> getAuctionModelsByColor(@PathVariable String color) {
        return auctionServices.getAuctionModelsByColor(color);
    }

    //get FILTER by brand
    @GetMapping("/filterByBrand/{brand}")
    public List<AuctionsDTO> getAuctionModelsByBrand(@PathVariable String brand) {
        return auctionServices.getAuctionModelsByBrand(brand);
    }

    //get FILTER by YearManufactured
    @GetMapping("/filterByYearManufactured/{minYearManufactured}/{maxYearManufactured}")
    public List<AuctionsDTO> getAuctionModelsByYearManufactured(@PathVariable int minYearManufactured,@PathVariable int maxYearManufactured) {
        return auctionServices.getAuctionModelsByYearManufactured(minYearManufactured,maxYearManufactured);
    }

    //get FILTER by MilesDriven
    @GetMapping("/filterByMilesDriven/{minMilesDriven}/{maxMilesDriven}")
    public List<AuctionsDTO> getAuctionModelsByMilesDriven(@PathVariable int minMilesDriven,@PathVariable int maxMilesDriven) {
        return auctionServices.getAuctionModelsByMilesDriven(minMilesDriven,maxMilesDriven);
    }

    //get FILTER by CONDITION
    @GetMapping("/filterByCondition/{condition}")
    public List<AuctionsDTO> getAuctionModelsByCondition(@PathVariable String condition) {
        return auctionServices.getAuctionModelsByCondition(condition);
    }
}


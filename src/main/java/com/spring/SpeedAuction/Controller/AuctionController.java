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

    //SORT by yearManufactured
    @GetMapping("/SortByYearManufactured/{yearManufactured}")
    public List<AuctionModels> getAuctionModelsByYearManufactured(@PathVariable int yearManufactured) {
        return auctionServices.getAuctionModelsByYearManufactured(yearManufactured);
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


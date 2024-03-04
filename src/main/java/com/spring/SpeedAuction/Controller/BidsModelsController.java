package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Services.BidsModelsService;
import com.spring.SpeedAuction.dto.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "api/bids")
public class BidsModelsController {
    @Autowired
    BidsModelsService bidsModelsService;

    //POST Lägg till ett nytt bid
    @PostMapping
    public BidsModels createBidModel(@RequestBody BidsDTO bidsDTO) {
        return bidsModelsService.createBidModels(bidsDTO);
    }

    //GET /bids - Hämta en lista över alla bids.
    @GetMapping("/all")
    public List<BidsDTO> getAllBidsModels() {
        return bidsModelsService.getAllBidsModel();
    }

    //GET /bids/{id} - Hämta ett specifikt bid baserat på id.
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BidsModels getAllBidsModels(@PathVariable String id) {
        return bidsModelsService.getBidsModel(id);
    }

    //PUT /bids/{id} - Uppdatera ett specifikt bid baserat på id.
    @PutMapping("/{id}")
    public BidsModels updateBidModel(@PathVariable String id, @RequestBody BidsModels bidsModels) {
        return bidsModelsService.updateBidModels(id, bidsModels);
    }

    //DELETE /bids/{id} - Ta bort ett bid baserat på id.
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBidModel(@PathVariable String id){
         return bidsModelsService.deleteBidmodels(id);

    }

    //Hämtar alla bids med ett specifikt bidderId
    @GetMapping("/filterByBidderId/{bidderId}")
    public List<BidsModels> getBidsModelByUserId(@PathVariable UserModels bidderId) {
        return bidsModelsService.getBidsModelByUserId(bidderId);
    }

    //Hämtar alla bids med ett specifikt auctionId
    @GetMapping("/filterByAuctionId/{auctionId}")
    public List<BidsModels> getBidsModelByAuctionId(@PathVariable AuctionModels auctionId) {
        return bidsModelsService.getBidsModelByAuctionId(auctionId);
    }

}

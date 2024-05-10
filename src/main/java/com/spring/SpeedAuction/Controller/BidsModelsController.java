package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.dto.BidsDTO;
import com.spring.SpeedAuction.security.Services.BidsModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/bids")
public class BidsModelsController {
    @Autowired
    BidsModelsService bidsModelsService;

    //POST Lägg till ett nytt bid
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/{auctionId}")
    public BidsModels createBidModel(@PathVariable String auctionId, @RequestBody BidsDTO bidsDTO) {
        return bidsModelsService.createBidModels(auctionId, bidsDTO);
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public BidsModels updateBidModel(@PathVariable String id, @RequestBody BidsModels bidsModels) {
        return bidsModelsService.updateBidModels(id, bidsModels);
    }

    //DELETE /bids/{id} - Ta bort ett bid baserat på id.
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBidModel(@PathVariable String id){
         return bidsModelsService.deleteBidModels(id);

    }

    //Hämtar alla bids med ett specifikt bidderId, nyaste budet kommer först
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/filterByBidderId/{bidderId}")
    public List<BidsDTO> getBidsModelByUserId(@PathVariable String bidderId) {
        return bidsModelsService.getBidsModelByUserId(bidderId);
    }

    //Hämtar alla bids med ett specifikt auctionId, största budet kommer först
    @GetMapping("/filterByAuctionId/{auctionId}")
    public List<BidsDTO> getBidsModelByAuctionId(@PathVariable String auctionId) {
        return bidsModelsService.getBidsModelByAuctionId(auctionId);
    }

    //Hämtar det största budet (senaste budet på en auktion är alltid störst) i en auction
    @GetMapping("/getTopBidByAuctionId/{auctionId}")
    public BidsDTO getTopBidByAuctionId(@PathVariable String auctionId) {
        return bidsModelsService.getTopBidByAuctionId(auctionId);
    }
}

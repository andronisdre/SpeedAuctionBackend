package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.BidsModels;
<<<<<<< HEAD
=======
import com.spring.SpeedAuction.DTO.BidsDTO;
>>>>>>> OOAD
import com.spring.SpeedAuction.Security.Services.BidsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/bids")
public class BidsController {
    @Autowired
    BidsServices bidsService;

    //POST Lägg till ett nytt bid
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/{auctionId}/{userId}")
    public BidsModels createBidModel(@PathVariable String auctionId, @PathVariable String userId, @RequestBody BidsDTO bidsDTO) {
        return bidsService.createBidModels(auctionId, userId, bidsDTO);
    }

    //GET /bids - Hämta en lista över alla bids.
    @GetMapping("/all")
    public List<BidsDTO> getAllBidsModels() {
        return bidsService.getAllBidsModel();
    }


    //GET /bids/{id} - Hämta ett specifikt bid baserat på id.
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BidsModels getAllBidsModels(@PathVariable String id) {
        return bidsService.getBidsModel(id);
    }

    //PUT /bids/{id} - Uppdatera ett specifikt bid baserat på id.
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping("/{id}/{auctionId}")
    public BidsModels updateBidModel(@PathVariable String id, @PathVariable String auctionId, @RequestBody BidsModels bidsModels) {
        return bidsService.updateBidModels(id, auctionId, bidsModels);
    }

    //DELETE /bids/{id} - Ta bort ett bid baserat på id.
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBidModel(@PathVariable String id){
         return bidsService.deleteBidModels(id);

    }

    //Hämtar alla bids med ett specifikt bidderId, nyaste budet kommer först
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/filterByBidderId/{bidderId}")
    public List<BidsDTO> getBidsModelByUserId(@PathVariable String bidderId) {
        return bidsService.getBidsModelByUserId(bidderId);
    }

    //Hämtar alla bids med ett specifikt auctionId, största budet kommer först
    @GetMapping("/filterByAuctionId/{auctionId}")
    public List<BidsDTO> getBidsModelByAuctionId(@PathVariable String auctionId) {
        return bidsService.getBidsModelByAuctionId(auctionId);
    }

    //Hämtar det största budet (senaste budet på en auktion är alltid störst) i en auction
    @GetMapping("/getTopBidByAuctionId/{auctionId}")
    public BidsDTO getTopBidByAuctionId(@PathVariable String auctionId) {
        return bidsService.getTopBidByAuctionId(auctionId);
    }
}

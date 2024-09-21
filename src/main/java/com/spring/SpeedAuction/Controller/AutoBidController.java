package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.DTO.AutoBidDTO;
import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Security.Services.AutoBidServices;
import com.spring.SpeedAuction.Security.Services.BidsServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auto-bid")
public class AutoBidController {


    final AutoBidServices autoBidServices;
    final BidsServices bidsServices;

    public AutoBidController(AutoBidServices autoBidServices, BidsServices bidsServices) {
        this.autoBidServices = autoBidServices;
        this.bidsServices = bidsServices;
    }

    @PostMapping
    public AutoBidDTO setAutoBid(@RequestBody AutoBidDTO autoBidDTO) {
        return autoBidServices.convertAutoBidDTO(autoBidServices.setAutoBid(autoBidDTO));
    }

    // place manual bid and trigger if necessary
    @PostMapping("/manual-bid/{auctionId}/{userId}")
    public BidsModels placeManualBid(@PathVariable String auctionId, @PathVariable String userId, @RequestBody BidsDTO amount) {
        return bidsServices.createBidModels(auctionId, userId, amount);
    }

}
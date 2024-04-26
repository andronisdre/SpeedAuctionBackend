package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.dto.AuctionsDTO;
import com.spring.SpeedAuction.security.Services.AuctionServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/auctions")
public class AuctionController {
    @Autowired
    AuctionServices auctionServices;


    //POST
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping()
    public AuctionModels createAuctionModels(@Valid @RequestBody AuctionsDTO auctionsDTO) {
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping(value = "/{id}")
    public AuctionModels updateAuctionModels(@PathVariable String id, @RequestBody AuctionModels auctionModels) {
        return auctionServices.updateAuctionModels(id, auctionModels);
    }

    //DELETE by id
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
}


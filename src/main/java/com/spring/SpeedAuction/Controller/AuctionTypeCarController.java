package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionTypeCar;
import com.spring.SpeedAuction.Services.AuctionTypeCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/auctionTypeCar")
public class AuctionTypeCarController {

    @Autowired
    AuctionTypeCarService auctionTypeCarService;


    //POST
    @PostMapping()
    public AuctionTypeCar createAuctionTypeCar(@RequestBody AuctionTypeCar auctionTypeCar) {
        return auctionTypeCarService.createAuctionTypeCar(auctionTypeCar);
    }

    //GET ALL
    @GetMapping("/all")
    public List<AuctionTypeCar> getAllAuctionTypeCars() {
        return auctionTypeCarService.getAllAuctionTypeCars();
    }

    //PUT by id
    @PutMapping(value = "/{id}")
    public AuctionTypeCar updateAuctionTypeCar(@RequestBody AuctionTypeCar auctionTypeCar) {
        return auctionTypeCarService.updateAuctionTypeCar(auctionTypeCar);
    }

    //GET by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AuctionTypeCar getAuctionTypeCarById(@PathVariable String id) {
        return auctionTypeCarService.getAuctionTypeCarById(id);
    }

    //DELETE by id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAuctionTypeCar(@PathVariable String id) {
        return auctionTypeCarService.deleteAuctionTypeCar(id);
    }
}


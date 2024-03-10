package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.AuctionTypeCar;
import com.spring.SpeedAuction.Repository.AuctionTypeCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionTypeCarService {
    @Autowired
    AuctionTypeCarRepository auctionTypeCarRepository;

    //post
    public AuctionTypeCar createAuctionTypeCar(AuctionTypeCar auctionTypeCar) {
        return auctionTypeCarRepository.save(auctionTypeCar);
    }

    //get all
    public List<AuctionTypeCar> getAllAuctionTypeCars() {
        return auctionTypeCarRepository.findAll();
    }


    //update
    public AuctionTypeCar updateAuctionTypeCar(AuctionTypeCar auctionTypeCar) {
        return auctionTypeCarRepository.save(auctionTypeCar);
    }

    //get auction by id
    public AuctionTypeCar getAuctionTypeCarById(String id) {
        return auctionTypeCarRepository.findById(id).get();
    }

    //delete auction
    public String deleteAuctionTypeCar(String id) {
        auctionTypeCarRepository.deleteById(id);
        return "AuctionTypeCar deleted";
    }
}

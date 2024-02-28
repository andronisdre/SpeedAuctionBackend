package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServices {
    @Autowired
    AuctionRepository auctionRepository;

    //post
    public AuctionModels createAuctionModels(AuctionModels auctionModels) {
        return auctionRepository.save(auctionModels);
    }

    //get all
    public List<AuctionModels> getAllAuctionModels() {
        return auctionRepository.findAll();
    }


    //update
    public AuctionModels updateAuctionModels(AuctionModels auctionModels) {
        return auctionRepository.save(auctionModels);
    }

    //get auction by id
    public AuctionModels getAuctionModelsById(String id) {
        return auctionRepository.findById(id).get();
    }

    //get all auctions in the range between minYearManufactured and maxYearManufactured
    public List<AuctionModels> getAuctionModelsByYearManufacturedBetween(int minYearManufactured, int maxYearManufactured) {
        return auctionRepository.findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc(minYearManufactured, maxYearManufactured);
    }

    //get all auctions that are either active or inactive
    public List<AuctionModels> getAuctionModelsByIsActive(boolean isActive) {
        return auctionRepository.findAuctionModelsByIsActive(isActive);
    }

    //get all auctions that match with brand
    public List<AuctionModels> getAuctionModelsByBrand(String brand) {
        return auctionRepository.findAuctionModelsByBrand(brand);
    }

    //get all auctions in the range between minStartingBid and maxStartingBid
    public List<AuctionModels> getAuctionModelsByStartingBidBetween(int minStartingBid, int maxStartingBid) {
        return auctionRepository.findAuctionModelsByStartingBidBetweenOrderByStartingBidAsc(minStartingBid, maxStartingBid);
    }

    //get all auctions in the range between minMilesDriven and maxMilesDriven
    public List<AuctionModels> getAuctionModelsByMilesDrivenBetween(int minMilesDriven, int maxMilesDriven) {
        return auctionRepository.findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc(minMilesDriven, maxMilesDriven);
    }

    //customised filter to choose between different fields to filter by in a single query
    public List<AuctionModels> getAuctionModelsByCustomFilter(int minStartingBid, int maxStartingBid, int yearManufactured, boolean isActive) {
        return auctionRepository.findAuctionModelsByStartingBidBetweenAndYearManufacturedAndIsActiveOrderByStartingBidAsc(minStartingBid, maxStartingBid, yearManufactured, isActive);
    }

    //delete auction
    public String deleteAuctionModels(String id) {
        auctionRepository.deleteById(id);
        return "Auction deleted";
    }
}

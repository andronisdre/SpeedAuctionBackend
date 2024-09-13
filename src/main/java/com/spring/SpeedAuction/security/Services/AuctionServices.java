package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.dto.AuctionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServices {
    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    //post
    public AuctionModels createAuctionModels(AuctionsDTO auctionsDTO, String userId) {
        UserModels user = checkUserId(userId);
        AuctionModels newAuction = retrieveData(auctionsDTO, user);
        newAuction.setCreated_at(new Date());
        checkEndOfAuction(newAuction);
        newAuction.setActive(true);
        newAuction.setCarModel(auctionsDTO.getCarModel());
        newAuction.setBrand(auctionsDTO.getBrand());
        newAuction.setCarPng(auctionsDTO.getCarPng());
        newAuction.setColor(auctionsDTO.getColor());
        newAuction.setCondition(auctionsDTO.getCondition());
        newAuction.setMilesDriven(auctionsDTO.getMilesDriven());
        newAuction.setDescription(auctionsDTO.getDescription());
        newAuction.setRegNumber(auctionsDTO.getRegNumber());
        newAuction.setYearManufactured(auctionsDTO.getYearManufactured());

        return auctionRepository.save(newAuction);
    }

    //get all
    public List<AuctionsDTO> getAllAuctionModels() {
        List<AuctionModels> auctionModels = auctionRepository.findAll();
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get auction by id
    public AuctionModels getAuctionModelsById(String id) {
        return auctionRepository.findById(id).get();
    }

    //update
    public AuctionModels updateAuctionModels(String id, AuctionModels auctionModels) {
        AuctionModels existingAuction = auctionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));

        //update field
        if (auctionModels.getStartingPrice() != 0){
            existingAuction.setStartingPrice(auctionModels.getStartingPrice());
        }
        if (auctionModels.getEndOfAuction() != null){
            existingAuction.setEndOfAuction(auctionModels.getEndOfAuction());
        }
        if (auctionModels.getCondition() != null){
            existingAuction.setCondition(auctionModels.getCondition());
        }
        if (auctionModels.getMilesDriven() != 0){
            existingAuction.setMilesDriven(auctionModels.getMilesDriven());
        }
        if (auctionModels.getCarModel() != null){
            existingAuction.setCarModel(auctionModels.getCarModel());
        }
        if (auctionModels.getBrand() != null){
            existingAuction.setBrand(auctionModels.getBrand());
        }
        if (auctionModels.getYearManufactured() != 0) {
            existingAuction.setYearManufactured(auctionModels.getYearManufactured());
        }
        if (auctionModels.getColor() != null){
            existingAuction.setColor(auctionModels.getColor());
        }
        if (auctionModels.getRegNumber() != null){
            existingAuction.setRegNumber(auctionModels.getRegNumber());
        }
        if (auctionModels.getCarPng() != null){
            existingAuction.setCarPng(auctionModels.getCarPng());
        }
        if (auctionModels.getDescription() != null){
            existingAuction.setDescription(auctionModels.getDescription());
        }

        existingAuction.setSeller(existingAuction.getSeller());
        existingAuction.setCreated_at(existingAuction.getCreated_at());
        existingAuction.setUpdated_at(new Date());
        checkEndOfAuction(existingAuction);

        return auctionRepository.save(existingAuction);
    }

    //delete auction
    public String deleteAuctionModels(String id) {
        AuctionModels auctionModels = auctionRepository.findById(id).orElse(null);
        if (auctionModels != null) {
            auctionRepository.deleteById(id);
            return "auction deleted";
        }
        else {
            return "auction id doesnt exist";
        }
    }

    //get all auctions that are either active or inactive
    public List<AuctionsDTO> getAuctionModelsByIsActive(boolean isActive) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByIsActive(isActive);
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for that value");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions in the range between minStartingBid and maxStartingBid
    public List<AuctionsDTO> getAuctionModelsByStartingPriceBetween(int minStartingPrice, int maxStartingPrice) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByStartingPriceBetweenOrderByStartingPriceAsc((minStartingPrice - 1), (maxStartingPrice + 1));
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for those values");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with color
    public List<AuctionsDTO> getAuctionModelsByColor(String color) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByColor(color);

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with brand
    public List<AuctionsDTO> getAuctionModelsByBrand(String brand) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByBrand(brand);

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with YearManufactured
    public List<AuctionsDTO> getAuctionModelsByYearManufactured(int minYearManufactured, int maxYearManufactured) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc((minYearManufactured - 1),(maxYearManufactured + 1));

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with MilesDriven
    public List<AuctionsDTO> getAuctionModelsByMilesDriven(int minMilesDriven, int maxMilesDriven) {
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc((minMilesDriven - 1),(maxMilesDriven + 1));

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with CONDITION
    public List<AuctionsDTO> getAuctionModelsByCondition(String condition){
        List<AuctionModels> auctionModels = auctionRepository.findAuctionModelsByCondition(condition);

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // UTIL functions
    public UserModels checkUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    public void checkEndOfAuction(AuctionModels auctionModels) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(auctionModels.getCreated_at());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date minCreated_at = calendar.getTime();

        if(auctionModels.getEndOfAuction().before(minCreated_at)) {
            throw new IllegalArgumentException("date endOfAuction needs to be at least a day later than date created_at");
        }
    }

    private AuctionsDTO convertToDTO(AuctionModels auctionModels) {
        AuctionsDTO auctionsDTO = new AuctionsDTO();
        auctionsDTO.setId(auctionModels.getId());
        auctionsDTO.setSellerId(auctionModels.getSeller().getId());
        auctionsDTO.setActive(auctionModels.isActive());
        auctionsDTO.setStartingPrice(auctionModels.getStartingPrice());
        auctionsDTO.setEndOfAuction(auctionModels.getEndOfAuction());
        auctionsDTO.setCreated_at(auctionModels.getCreated_at());

        auctionsDTO.setBrand(auctionModels.getBrand());
        auctionsDTO.setCarModel(auctionModels.getCarModel());
        auctionsDTO.setCarPng(auctionModels.getCarPng());
        auctionsDTO.setColor(auctionModels.getColor());
        auctionsDTO.setCondition(auctionModels.getCondition());
        auctionsDTO.setMilesDriven(auctionModels.getMilesDriven());
        auctionsDTO.setDescription(auctionModels.getDescription());
        auctionsDTO.setRegNumber(auctionModels.getRegNumber());
        auctionsDTO.setYearManufactured(auctionModels.getYearManufactured());

        return auctionsDTO;
    }

    public AuctionModels retrieveData(AuctionsDTO auctionsDTO, UserModels user) {
        AuctionModels newAuction = new AuctionModels.AuctionBuilder().build();
        newAuction.setSeller(user);
        newAuction.setActive(auctionsDTO.isActive());
        newAuction.setStartingPrice(auctionsDTO.getStartingPrice());
        newAuction.setEndOfAuction(auctionsDTO.getEndOfAuction());
        newAuction.setCreated_at(auctionsDTO.getCreated_at());

        return newAuction;
    }
}

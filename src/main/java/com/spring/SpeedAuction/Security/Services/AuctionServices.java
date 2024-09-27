package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.AuctionsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionFilterActive;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionFilterDetails;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionFilterPrice;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserInterfaces.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServices {

    private final AuctionRepository auctionRepository;
    private final AuctionFilterActive auctionFilterActive;
    private final AuctionFilterPrice auctionFilterPrice;
    private final AuctionFilterDetails auctionFilterDetails;
    private final UserRepository userRepository;

    public AuctionServices(AuctionRepository auctionRepository, AuctionFilterActive auctionFilterActive, AuctionFilterPrice auctionFilterPrice, AuctionFilterDetails auctionFilterDetails, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionFilterActive = auctionFilterActive;
        this.auctionFilterPrice = auctionFilterPrice;
        this.auctionFilterDetails = auctionFilterDetails;
        this.userRepository = userRepository;
    }

    //post 123
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

        // Anropa valideringsmetoden här
        validateAuction(newAuction);

        return auctionRepository.save(newAuction);
    }

    //get all
    public List<AuctionsDTO> getAllAuctionModels() {
        List<AuctionModels> auctionModels = auctionRepository.findAll();
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get auction by id... Använde orElseThrow istället för.get() för att hantera när ett objekt inte hittas.
    public AuctionModels getAuctionModelsById(String id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Auction with id " + id + " not found"));
    }

    //update
    public AuctionModels updateAuctionModels(String id, AuctionModels auctionModels) {
        AuctionModels existingAuction = auctionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));

        if (auctionModels.getStartingPrice() != 0) {
            existingAuction.setStartingPrice(auctionModels.getStartingPrice());
        }
        if (auctionModels.getEndOfAuction() != null) {
            existingAuction.setEndOfAuction(auctionModels.getEndOfAuction());
        }
        if (auctionModels.getCondition() != null) {
            existingAuction.setCondition(auctionModels.getCondition());
        }
        if (auctionModels.getMilesDriven() != 0) {
            existingAuction.setMilesDriven(auctionModels.getMilesDriven());
        }
        if (auctionModels.getCarModel() != null) {
            existingAuction.setCarModel(auctionModels.getCarModel());
        }
        if (auctionModels.getBrand() != null) {
            existingAuction.setBrand(auctionModels.getBrand());
        }
        if (auctionModels.getYearManufactured() != 0) {
            existingAuction.setYearManufactured(auctionModels.getYearManufactured());
        }
        if (auctionModels.getColor() != null) {
            existingAuction.setColor(auctionModels.getColor());
        }
        if (auctionModels.getRegNumber() != null) {
            existingAuction.setRegNumber(auctionModels.getRegNumber());
        }
        if (auctionModels.getCarPng() != null) {
            existingAuction.setCarPng(auctionModels.getCarPng());
        }
        if (auctionModels.getDescription() != null) {
            existingAuction.setDescription(auctionModels.getDescription());
        }

        existingAuction.setSeller(existingAuction.getSeller());
        existingAuction.setBids(existingAuction.getBids());
        existingAuction.setCreated_at(existingAuction.getCreated_at());
        existingAuction.setUpdated_at(new Date());
        checkEndOfAuction(existingAuction);

        // Anropar valideringsmetoden här
        validateAuction(existingAuction);

        return auctionRepository.save(existingAuction);
    }
    // Valideringsmetod för att säkerställa att auktionen har korrekta och giltiga värden innan den sparas i databasen.
    private void validateAuction(AuctionModels auctionModels) {
        if (auctionModels.getStartingPrice() <= 0) {
            throw new IllegalArgumentException("Starting price must be greater than zero");
        }
        if (auctionModels.getCarModel() == null || auctionModels.getCarModel().isEmpty()) {
            throw new IllegalArgumentException("Car model is required");
        }
        if (auctionModels.getBrand() == null || auctionModels.getBrand().equals("")) {
            throw new IllegalArgumentException("Brand is required");
        }

        if (auctionModels.getYearManufactured() <= 0) {
            throw new IllegalArgumentException("Year manufactured must be valid");
        }
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
        List<AuctionModels> auctionModels = auctionFilterActive.findAuctionModelsByIsActive(isActive);
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for that value");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions in the range between minStartingBid and maxStartingBid
    public List<AuctionsDTO> getAuctionModelsByStartingPriceBetween(int minStartingPrice, int maxStartingPrice) {
        List<AuctionModels> auctionModels = auctionFilterPrice.findAuctionModelsByStartingPriceBetweenOrderByStartingPriceAsc((minStartingPrice - 1), (maxStartingPrice + 1));
        if (auctionModels.isEmpty()) {
            throw new IllegalArgumentException("no results for those values");
        }
        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with color
    public List<AuctionsDTO> getAuctionModelsByColor(String color) {
        List<AuctionModels> auctionModels = auctionFilterDetails.findAuctionModelsByColor(color);

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with brand
    public List<AuctionsDTO> getAuctionModelsByBrand(String brand) {
        List<AuctionModels> auctionModels = auctionFilterDetails.findAuctionModelsByBrand(brand);

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with YearManufactured
    public List<AuctionsDTO> getAuctionModelsByYearManufactured(int minYearManufactured, int maxYearManufactured) {
        List<AuctionModels> auctionModels = auctionFilterDetails.findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc((minYearManufactured - 1),(maxYearManufactured + 1));

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with MilesDriven
    public List<AuctionsDTO> getAuctionModelsByMilesDriven(int minMilesDriven, int maxMilesDriven) {
        List<AuctionModels> auctionModels = auctionFilterDetails.findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc((minMilesDriven - 1),(maxMilesDriven + 1));

        return auctionModels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with CONDITION
    public List<AuctionsDTO> getAuctionModelsByCondition(String condition){
        List<AuctionModels> auctionModels = auctionFilterDetails.findAuctionModelsByCondition(condition);

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

        if (auctionModels.getBids() != null && !auctionModels.getBids().isEmpty()) {
            auctionsDTO.setBids(auctionModels.getBids().stream()
                    .map(BidsModels::getId).collect(Collectors.toList()));
        } else {
            auctionsDTO.setBids(Collections.emptyList());
        }

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

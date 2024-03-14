package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.AuctionTypeCar;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.AuctionTypeCarRepository;
import com.spring.SpeedAuction.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionTypeCarService {
    @Autowired
    AuctionTypeCarRepository auctionTypeCarRepository;
    @Autowired
    AuctionRepository auctionRepository;

    //update
    public AuctionTypeCar updateAuctionTypeCar(String id, AuctionTypeCar auctionTypeCar) {
        AuctionTypeCar existingAuctionTypeCar = auctionTypeCarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));
        existingAuctionTypeCar.setId(id);

        //update field
        if (auctionTypeCar.getCondition() != null){
            existingAuctionTypeCar.setCondition(auctionTypeCar.getCondition());
        }
        if (auctionTypeCar.getMilesDriven() != 0){
            existingAuctionTypeCar.setMilesDriven(auctionTypeCar.getMilesDriven());
        }
        if (auctionTypeCar.getCarModel() != null){
            existingAuctionTypeCar.setCarModel(auctionTypeCar.getCarModel());
        }
        if (auctionTypeCar.getBrand() != null){
            existingAuctionTypeCar.setBrand(auctionTypeCar.getBrand());
        }
        if (auctionTypeCar.getYearManufactured() != 0) {
            existingAuctionTypeCar.setYearManufactured(auctionTypeCar.getYearManufactured());
        }
        if (auctionTypeCar.getColor() != null){
            existingAuctionTypeCar.setColor(auctionTypeCar.getColor());
        }
        if (auctionTypeCar.getRegNumber() != null){
            existingAuctionTypeCar.setRegNumber(auctionTypeCar.getRegNumber());
        }
        if (auctionTypeCar.getCarPng() != null){
            existingAuctionTypeCar.setCarPng(auctionTypeCar.getCarPng());
        }
        if (auctionTypeCar.getDescription() != null){
            existingAuctionTypeCar.setDescription(auctionTypeCar.getDescription());
        }

        return auctionTypeCarRepository.save(existingAuctionTypeCar);
    }


    public AuctionTypeCar getAuctionTypeCarById(String id) {
        return auctionTypeCarRepository.findById(id).get();
    }

    public String deleteAuctionTypeCar(String id) {
        AuctionTypeCar auctionTypeCar = auctionTypeCarRepository.findById(id).orElse(null);
        if (auctionTypeCar != null) {
            auctionTypeCarRepository.deleteById(id);
            return "auctionTypeCar deleted";
        }
        else {
            return "auctionTypeCar id doesnt exist";
        }
    }
    public AuctionTypeCar createAuctiontypecar (CarDTO auctionTypeCarDTO) {

        AuctionModels auction = auctionRepository.findById(auctionTypeCarDTO.getAuctionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid AuctionId"));



        AuctionTypeCar newAuctionTypeCar = new AuctionTypeCar();
        newAuctionTypeCar.setAuction(auction);
        newAuctionTypeCar.setCarModel(auctionTypeCarDTO.getCarModel());
        newAuctionTypeCar.setBrand(auctionTypeCarDTO.getBrand());
        newAuctionTypeCar.setCarPng(auctionTypeCarDTO.getCarPng());
        newAuctionTypeCar.setColor(auctionTypeCarDTO.getColor());
        newAuctionTypeCar.setCondition(auctionTypeCarDTO.getCondition());
        newAuctionTypeCar.setMilesDriven(auctionTypeCarDTO.getMilesDriven());
        newAuctionTypeCar.setDescription(auctionTypeCarDTO.getDescription());
        newAuctionTypeCar.setRegNumber(auctionTypeCarDTO.getRegNumber());
        newAuctionTypeCar.setYearManufactured(auctionTypeCarDTO.getYearManufactured());

        AuctionModels existingAuction = auctionRepository.findById(newAuctionTypeCar.getAuction().getId()).orElseThrow(() -> new IllegalArgumentException("auction does not exist"));
        existingAuction.setActive(true);
        existingAuction.setSeller(existingAuction.getSeller());
        existingAuction.setStartingPrice(existingAuction.getStartingPrice());
        existingAuction.setCreated_at(existingAuction.getCreated_at());
        existingAuction.setEndOfAuction(existingAuction.getEndOfAuction());
        existingAuction.setUpdated_at(existingAuction.getUpdated_at());
        existingAuction.setId(existingAuction.getId());
        auctionRepository.save(existingAuction);

        return  auctionTypeCarRepository.save(newAuctionTypeCar);
    }


    public List<CarDTO> getAllAuctionTypeCar() {
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAll();

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private CarDTO convertToDTO(AuctionTypeCar auctionTypeCar) {
        CarDTO carDTOResponse = new CarDTO();
        carDTOResponse.setAuctionId(auctionTypeCar.getAuction().getId());
        carDTOResponse.setBrand(auctionTypeCar.getBrand());
        carDTOResponse.setCarModel(auctionTypeCar.getCarModel());
        carDTOResponse.setCarPng(auctionTypeCar.getCarPng());
        carDTOResponse.setColor(auctionTypeCar.getColor());
        carDTOResponse.setCondition(auctionTypeCar.getCondition());
        carDTOResponse.setMilesDriven(auctionTypeCar.getMilesDriven());
        carDTOResponse.setDescription(auctionTypeCar.getDescription());
        carDTOResponse.setRegNumber(auctionTypeCar.getRegNumber());
        carDTOResponse.setYearManufactured(auctionTypeCar.getYearManufactured());


        return carDTOResponse;
    }

    //get all auctions that match with color
    public List<CarDTO> getAuctionTypeCarByColor(String color) {
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAuctionTypeCarByColor(color);

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with brand
    public List<CarDTO> getAuctionTypeCarByBrand(String brand) {
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAuctionTypeCarByBrand(brand);

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with YearManufactured
    public List<CarDTO> getAuctionTypeCarByYearManufactured(int minYearManufactured, int maxYearManufactured) {
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAuctionTypeCarByYearManufacturedBetweenOrderByYearManufacturedDesc(minYearManufactured,maxYearManufactured);

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with MilesDriven
    public List<CarDTO> getAuctionTypeCarByMilesDriven(int minMilesDriven, int maxMilesDriven) {
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAuctionTypeCarByMilesDrivenBetweenOrderByMilesDrivenAsc(minMilesDriven,maxMilesDriven);

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get all auctions that match with CONDITION
    public List<CarDTO> getAuctionTypeCarByCondition(String condition){
        List<AuctionTypeCar> auctionTypeCars = auctionTypeCarRepository.findAuctionTypeCarByCondition(condition);

        return auctionTypeCars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

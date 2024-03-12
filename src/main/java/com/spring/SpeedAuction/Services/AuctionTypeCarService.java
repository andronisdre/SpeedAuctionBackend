package com.spring.SpeedAuction.Services;

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
        if (auctionTypeCar.getCar_model() != null){
            existingAuctionTypeCar.setCar_model(auctionTypeCar.getCar_model());
        }
        if (auctionTypeCar.getBrand() != null){
            auctionTypeCar.setBrand(auctionTypeCar.getBrand());
        }

        return auctionTypeCarRepository.save(existingAuctionTypeCar);
    }


    public AuctionTypeCar getAuctionTypeCarById(String id) {
        return auctionTypeCarRepository.findById(id).get();
    }

    public String deleteAuctionTypeCar(String id) {
        auctionTypeCarRepository.deleteById(id);
        return "AuctionTypeCar deleted";
    }
    public AuctionTypeCar createAuctiontypecar (CarDTO auctionTypeCarDTO) {

        AuctionModels auction = auctionRepository.findById(auctionTypeCarDTO.getAuctionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid AuctionId"));



        AuctionTypeCar newAuctionTypeCar = new AuctionTypeCar();
        //newAuctionTypeCar.setId(auctionTypeCarDTO.getId());
        newAuctionTypeCar.setAuction(auction);
        newAuctionTypeCar.setCar_model(auctionTypeCarDTO.getCar_model());
        newAuctionTypeCar.setBrand(auctionTypeCarDTO.getBrand());
        newAuctionTypeCar.setCar_png(auctionTypeCarDTO.getCar_png());
        newAuctionTypeCar.setColor(auctionTypeCarDTO.getColor());
        newAuctionTypeCar.setCondition(auctionTypeCarDTO.getCondition());
        newAuctionTypeCar.setMilesDriven(auctionTypeCarDTO.getMilesDriven());
        newAuctionTypeCar.setDescription(auctionTypeCarDTO.getDescription());
        newAuctionTypeCar.setReg_number(auctionTypeCarDTO.getReg_number());
        newAuctionTypeCar.setYearManufactured(auctionTypeCarDTO.getYearManufactured());


        AuctionModels existingAuction = auctionRepository.findById(newAuctionTypeCar.getAuction().getId()).orElseThrow(() -> new IllegalArgumentException("auction does not exist"));
        existingAuction.setActive(true);
        existingAuction.setSeller(existingAuction.getSeller());
        existingAuction.setStartingBid(existingAuction.getStartingBid());
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
        //carDTOResponse.setId(auctionTypeCar.getId());
        carDTOResponse.setAuctionId(auctionTypeCar.getAuction().getId());
        carDTOResponse.setBrand(auctionTypeCar.getBrand());
        carDTOResponse.setCar_model(auctionTypeCar.getCar_model());
        carDTOResponse.setCar_png(auctionTypeCar.getCar_png());
        carDTOResponse.setColor(auctionTypeCar.getColor());
        carDTOResponse.setCondition(auctionTypeCar.getCondition());
        carDTOResponse.setMilesDriven(auctionTypeCar.getMilesDriven());
        carDTOResponse.setDescription(auctionTypeCar.getDescription());
        carDTOResponse.setReg_number(auctionTypeCar.getReg_number());
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

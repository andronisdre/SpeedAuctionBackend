package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.AuctionTypeCar;
import com.spring.SpeedAuction.dto.CarDTO;
import com.spring.SpeedAuction.security.Services.AuctionTypeCarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/api/auctionTypeCar")
public class AuctionTypeCarController {

    @Autowired
    AuctionTypeCarService auctionTypeCarService;


    //POST
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/{auctionId}")
    public AuctionTypeCar createAuctionTypeCar(@PathVariable String auctionId, @Valid @RequestBody CarDTO carDTO) {
        return auctionTypeCarService.createAuctiontypecar(carDTO, auctionId);
    }

    //GET ALL
    @GetMapping("/all")
    public List<CarDTO> getAllAuctionTypeCars() {
        return auctionTypeCarService.getAllAuctionTypeCar();
    }

    //PUT by id
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping(value = "/{id}")
    public AuctionTypeCar updateAuctionTypeCar(@PathVariable String id, @RequestBody AuctionTypeCar auctionTypeCar) {
        return auctionTypeCarService.updateAuctionTypeCar(id, auctionTypeCar);
    }

    //GET by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AuctionTypeCar getAuctionTypeCarById(@PathVariable String id) {
        return auctionTypeCarService.getAuctionTypeCarById(id);
    }

    //DELETE by id
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteAuctionTypeCar(@PathVariable String id) {
        return auctionTypeCarService.deleteAuctionTypeCar(id);
    }

    //get FILTER by auctionId
    @GetMapping("/filterByAuction/{auctionId}")
    public List<CarDTO> getAuctionTypeCarByAuction(@PathVariable String auctionId) {
        return auctionTypeCarService.getAuctionTypeCarByAuction(auctionId);
    }

    //get FILTER by color
    @GetMapping("/filterByColor/{color}")
    public List<CarDTO> getAuctionTypeCarByColor(@PathVariable String color) {
        return auctionTypeCarService.getAuctionTypeCarByColor(color);
    }

    //get FILTER by brand
    @GetMapping("/filterByBrand/{brand}")
    public List<CarDTO> getAuctionTypeCarByBrand(@PathVariable String brand) {
        return auctionTypeCarService.getAuctionTypeCarByBrand(brand);
    }

    //get FILTER by YearManufactured
    @GetMapping("/filterByYearManufactured/{minYearManufactured}/{maxYearManufactured}")
    public List<CarDTO> getAuctionTypeCarByYearManufactured(@PathVariable int minYearManufactured,@PathVariable int maxYearManufactured) {
        return auctionTypeCarService.getAuctionTypeCarByYearManufactured(minYearManufactured,maxYearManufactured);
    }

    //get FILTER by MilesDriven
    @GetMapping("/filterByMilesDriven/{minMilesDriven}/{maxMilesDriven}")
    public List<CarDTO> getAuctionTypeCarByMilesDriven(@PathVariable int minMilesDriven,@PathVariable int maxMilesDriven) {
        return auctionTypeCarService.getAuctionTypeCarByMilesDriven(minMilesDriven,maxMilesDriven);
    }

    //get FILTER by CONDITION
    @GetMapping("/filterByCondition/{condition}")
    public List<CarDTO> getAuctionTypeCarByCondition(@PathVariable String condition) {
        return auctionTypeCarService.getAuctionTypeCarByCondition(condition);
    }
}


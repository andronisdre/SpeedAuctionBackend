package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.security.Services.BidsModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "api/bids")
public class BidsModelsController {
    @Autowired
    BidsModelsService bidsModelsService;

    //POST Lägg till ett nytt bid
    @PostMapping
    public BidsModels createBidModel(@RequestBody BidsModels bidsModel) {
        return bidsModelsService.createBidModels(bidsModel);
    }

    //GET /bids - Hämta en lista över alla recept.
    @GetMapping("/all")
    public List<BidsModels> getAllBidsModels() {
        return bidsModelsService.getAllBidsModel();
    }

    //GET /bids/{id} - Hämta ett specifikt recept baserat på id.
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BidsModels getAllBidsModels(@PathVariable String id) {
        return bidsModelsService.getBidsModel(id);
    }

    //PUT /bids/{id} - Uppdatera ett specifikt recept baserat på id.
    @PutMapping("/{id}")
    public BidsModels updateBidModel(@PathVariable String id, @RequestBody BidsModels bidsModels) {
        return bidsModelsService.updateBidModels(id, bidsModels);
    }

    //DELETE /bids/{id} - Ta bort ett recept baserat på id.
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBidModel(@PathVariable String id){
         return bidsModelsService.deleteBidmodels(id);

    }

}

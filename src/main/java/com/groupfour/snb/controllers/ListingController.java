package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/listing")
public class ListingController {
    /*

 CRUD stands for:
    C - create
    R - read
    U - update
    D - delete

 */
    @Autowired
    ListingService service;

    @PostMapping("/saveAll")
    public String postListings(){
        service.addListings();
        return "Saved all listings";
    }
    @GetMapping("/{listingId}")
    public Optional<Listing> getListingById(@PathVariable("listingId") int listingId){
        return service.getListingsById(listingId);
    }
    @GetMapping("/getAll")
    public Iterable<Listing> getAllListings(){
        return service.getAllListings();
    }
}

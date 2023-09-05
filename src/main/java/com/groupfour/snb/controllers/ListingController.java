package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @PostMapping(path = "/saveAll")
    public String postListings(){
        service.addListings();
        return "Saved all listings";
    }
    @GetMapping(path = "/{listingId}")
    public Optional<Listing> getListingById(@PathVariable("listingId") UUID listingId){
        return service.getListingsById(listingId);
    }
    @GetMapping(path = "/getAll")
    public Iterable<Listing> getAllListings(){
        return service.getAllListings();
    }
}

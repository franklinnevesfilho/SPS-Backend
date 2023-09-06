package com.groupfour.snb.controllers;

import com.groupfour.snb.models.Listing;
import com.groupfour.snb.services.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// The all args constructor is used so Spring injects the services
@AllArgsConstructor
@RestController
@RequestMapping("/listing")
public class ListingController {
    private ListingService service;

    //A post request, inside the body will have a listing, and within the param the userId.
    @PostMapping(path = "/add")
    public String addListing(@RequestBody Listing listing, @RequestParam("user_id") UUID id){
        service.addListing(listing,id);
        return "successfully added";
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

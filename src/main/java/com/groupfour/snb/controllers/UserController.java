package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.DTO.CreateListing;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.utils.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <h1>UserController</h1>
 * This controller will be our users access point, allowing them to perform actions
 * To access this controller their userId must be passed within the path.
 * @author Franklin Neves Filho
 */
@RestController
@RequestMapping("/user")
public class UserController extends MainController {
    private ListingService listingService;
    private final BiFunction<String, Object, Response> CREATE_LISTING = (userId, listing) -> listingService.addListing((CreateListing) listing, userId);
    private final Function<String, Response> GET_ALL_LISTINGS = (userId) -> listingService.getAllListingsWithUser(userId);
    public UserController(ListingService listingService){
        this.listingService = listingService;
    }

    @GetMapping("/personal-listings")
    public ResponseEntity<Response> getPersonalListings(Authentication user) {
        return genericGetByParameter(GET_ALL_LISTINGS, user.getName());
    }

    @GetMapping("/get-all-listings")
    public ResponseEntity<Response> getAllListings(){
        return factory.generateNoContentResponse();
    }

    @PostMapping("/create-listing")
    public ResponseEntity<Response> createListing(Authentication user, @RequestBody CreateListing listing) {
        return genericGetByTwoParameter(CREATE_LISTING, user.getName(), listing);
    }

    @DeleteMapping("/delete-listing/{id}")
    public ResponseEntity<Response> deleteListing(Authentication user, @PathVariable("id") String listingId){
    return factory.generateNoContentResponse();
    }

    @PostMapping("/update-listing")
    public ResponseEntity<Response> updateListing(Authentication user, @RequestBody Listing listing){
        return factory.generateNoContentResponse();
    }

    @GetMapping("/select-listing/{id}")
    public ResponseEntity<Response> getListing(@PathVariable("id") String listingId){
        return factory.generateNoContentResponse();
    }
}
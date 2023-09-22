package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.DTO.CreateListing;
import com.groupfour.snb.models.services.ListingService;
import com.groupfour.snb.utils.responses.Response;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/user/{userId}")
public class UserController extends MainController {
    private ListingService listingService;
    private final BiFunction<String, Object, Response> CREATE_LISTING = (userId, listing) -> listingService.addListing((CreateListing) listing, userId);
    private final Function<String, Response> GET_ALL_LISTINGS = (userId) -> listingService.getAllListingsWithUser(userId);
    public UserController(ListingService listingService){
        this.listingService = listingService;
    }
    @GetMapping()
    public ResponseEntity<Response> getAllListings(@PathVariable String userId) {
        return genericGetByParameter(GET_ALL_LISTINGS, userId);
    }
    @PostMapping("/create-listing")
    public ResponseEntity<Response> createListing(@PathVariable String userId, CreateListing listing) {
        return genericGetByTwoParameter(CREATE_LISTING, userId, listing);
    }
}
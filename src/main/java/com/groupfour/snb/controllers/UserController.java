package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.DTO.CreateListing;
import com.groupfour.snb.models.services.ListingService;
import com.groupfour.snb.utils.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @GetMapping("/listings")
    public ResponseEntity<Response> getAllListings(Principal user) {
        return genericGetByParameter(GET_ALL_LISTINGS, user.getName());
    }
    @PostMapping("/create-listing")
    public ResponseEntity<Response> createListing(Principal user,@RequestBody CreateListing listing) {
        return genericGetByTwoParameter(CREATE_LISTING, user.getName(), listing);
    }
}
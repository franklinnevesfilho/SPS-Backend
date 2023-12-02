package com.groupfour.sps.controllers;

import com.groupfour.sps.models.listing.DTO.CreateListing;
import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.services.ListingService;
import com.groupfour.sps.models.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <h1>Seller Controller</h1>
 *
 * Although not yet implemented this controller is responsible
 * for handling and updating listings and other seller actions within our system
 *
 * @author Franklin Neves
 */
@RestController
@RequestMapping("/seller")
public class SellerController extends MainController {

    private ListingService listingService;

    private final BiFunction<String, Object, Response> CREATE_LISTING = (userId, listing) -> listingService.addListing((CreateListing) listing, userId);
    private final Function<String, Response> GET_USER_LISTINGS = (userId) -> listingService.getAllListingsWithUser(userId);

    public SellerController(ListingService listingService){
        this.listingService = listingService;
    }

    /**
     * @param user the user is passed through the bearer token and used to identify which listings he has posted
     * @return all listings that the specified user has posted
     */
    @GetMapping("/get-posted-listings")
    public ResponseEntity<Response> getPostedListings(Authentication user) {
        return genericGetByParameter(GET_USER_LISTINGS, user.getName());
    }

    /**
     * @param user This user needs to have a seller role
     * @param listing This entity consists of title, description, and price.
     * @return will return the newly created listing object inside the Node attribute.
     */
    @PostMapping("/create-listing")
    public ResponseEntity<Response> createListing(Authentication user, @RequestBody CreateListing listing) {
        return genericGetByTwoParameter(CREATE_LISTING, user.getName(), listing);
    }

    /**
     * @param user The user must have the seller role
     * @param listingId this is used to identify the listing the user wishes to delete
     * @return Will return the updated user entity
     */
    @DeleteMapping("/delete-listing/{id}")
    public ResponseEntity<Response> deleteListing(Authentication user, @PathVariable("id") String listingId){
        return factory.generateNoContentResponse();
    }
    /**
     * @param user user must have the seller role
     * @param listing this is the updated listing object.
     * @return the updated listing
     */
    @PostMapping("/update-listing")
    public ResponseEntity<Response> updateListing(Authentication user, @RequestBody Listing listing){
        return factory.generateNoContentResponse();
    }
}

package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.DTO.CreateListing;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.UserService;
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
    private UserService userService;
    private final BiFunction<String, Object, Response> CREATE_LISTING = (userId, listing) -> listingService.addListing((CreateListing) listing, userId);
    private final Function<String, Response> GET_ALL_LISTINGS = (userId) -> listingService.getAllListingsWithUser(userId);
    private final BiFunction<String,Object,Response> SELLER_REQUEST = (userId, licenceNum) -> userService.sellerRequest(userId,(String)licenceNum);
    public UserController(ListingService listingService, UserService userService){
        this.listingService = listingService;
        this.userService = userService;
    }

    /**
     * @param user the user is passed through the bearer token and used to identify which listings he has posted
     * @return all listings that the specified user has posted
     */
    @GetMapping("/get-posted-listings")
    public ResponseEntity<Response> getPostedListings(Authentication user) {
        return genericGetByParameter(GET_ALL_LISTINGS, user.getName());
    }

    /**
     *
     * @return this will return all listings available for a user to purchase.
     */
    @GetMapping("/get-all-listings")
    public ResponseEntity<Response> getAllListings(){
        return factory.generateNoContentResponse();
    }

    /**
     *
     * @param user This user needs to have a seller role
     * @param listing This entity consists of title, description, and price.
     * @return will return the newly created listing object inside the Node attribute.
     */
    @PostMapping("/create-listing")
    public ResponseEntity<Response> createListing(Authentication user, @RequestBody CreateListing listing) {
        return genericGetByTwoParameter(CREATE_LISTING, user.getName(), listing);
    }

    /**
     *
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

    /**
     * @param listingId the listing id
     * @return just the listing entity that is requested
     */
    @GetMapping("/select-listing/{id}")
    public ResponseEntity<Response> getListing(@PathVariable("id") String listingId){
        return factory.generateNoContentResponse();
    }

    /**
     * @param user this is the user passed through the bearer token
     * @param licence the users driver's license number
     * @return the updated user with its roles.
     */
    @PostMapping("/seller-request")
    public ResponseEntity<Response> sellerRequest(Authentication user, @RequestBody String licence){
        return genericGetByTwoParameter(SELLER_REQUEST, user.getName(), licence);
    }
}
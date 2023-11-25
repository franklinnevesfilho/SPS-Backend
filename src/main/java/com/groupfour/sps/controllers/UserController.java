package com.groupfour.sps.controllers;

import com.groupfour.sps.services.ListingService;
import com.groupfour.sps.services.UserService;
import com.groupfour.sps.utils.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Supplier;

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
    private final BiFunction<String,Object,Response> SELLER_REQUEST = (userId, licenceNum) -> userService.sellerRequest(userId,(String)licenceNum);
    private final Supplier<Response> GET_ALL_LISTINGS = () -> listingService.getAllListings();

    public UserController(ListingService listingService, UserService userService){
        this.listingService = listingService;
        this.userService = userService;
    }

    /**
     * @return this will return all listings available for a user to purchase.
     */
    @GetMapping("/get-all-listings")
    public ResponseEntity<Response> getAllListings(){
        return genericGetAll(GET_ALL_LISTINGS);
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

    @GetMapping("/add-to-cart/{listingId}")
    public ResponseEntity<Response> addToCart(@PathVariable("listingId") String listingId, Authentication user){
        return factory.generateNoContentResponse();
    }
}
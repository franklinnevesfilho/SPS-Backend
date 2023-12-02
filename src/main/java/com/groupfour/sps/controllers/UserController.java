package com.groupfour.sps.controllers;

import com.groupfour.sps.models.PictureDTO;
import com.groupfour.sps.models.interfaces.Validator;
import com.groupfour.sps.models.user.DTO.SellerRequest;
import com.groupfour.sps.services.ListingService;
import com.groupfour.sps.services.UserService;
import com.groupfour.sps.models.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>UserController</h1>
 *
 * This controller will be our users access point,
 * allowing them to perform actions throughout the system
 * To access this controller a jwt token must be passed through the bearer token
 *
 * @author Franklin Neves Filho
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends MainController {
    private ListingService listingService;
    private UserService userService;
    private final BiFunction<String,Object,Response> SELLER_REQUEST = (userId, licenceNum) -> userService.sellerRequest(userId,(String)licenceNum);
    private final Supplier<Response> GET_ALL_LISTINGS = () -> listingService.getAllListings();
    private final Function<Validator, Response> GET_SELLER = (sellerId) -> userService.getSellerResponse((SellerRequest) sellerId);
    private final Function<String, Response> GET_LISTING = (listingId) -> listingService.getListingWithId(listingId);
    private final Function<String, Response> GET_CART = (userId) -> userService.getUserCart(userId);
    private final BiFunction<String, Object, Response> ADD_TO_CART = (listingId, userId) -> userService.addToCart(listingId,(String)userId);
    private final BiFunction<String, Object ,Response> REMOVE_FROM_CART = (listingId, userId) -> userService.removeFromCart(listingId, (String)userId);
    private final Function<String, Response> GET_USER = (userId) -> userService.getUser(userId);
    private final BiFunction<String, Object, Response> ADD_PROFILE = (userId, picture) -> userService.updateProfile(userId, (PictureDTO) picture);
    private final BiFunction<String, Object, Response> UPDATE_TWO_FACTOR = (userId, status) -> userService.updateTwoFactor(userId, (boolean) status);

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
        return genericGetByParameter(GET_LISTING, listingId);
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

    /**
     * @param sellerRequest contains the seller id of a certain listing
     * @return certain seller information to be displayed
     */
    @PostMapping("/get-seller")
    public ResponseEntity<Response> getSeller(@RequestBody SellerRequest sellerRequest){
        return genericRequest(GET_SELLER, sellerRequest);
    }

    /**
     * @param pic a base64 string representation of an image
     * @return the updated userProfile
     */
    @PostMapping("/update-profile-picture")
    public ResponseEntity<Response> updateProfilePicture(@RequestBody PictureDTO pic, Authentication user){
        log.info(pic.toString());
        return genericGetByTwoParameter(ADD_PROFILE, user.getName(), pic);
    }

    /**
     * Add a specific listing to the users cart
      * @param listing this is the listing id to be added
     * @param user through a bearer token the userId is able to be retrieved.
     * @return an updated cart
     */
    @GetMapping("/add-to-cart/")
    public ResponseEntity<Response> addToCart(@RequestParam String listing, Authentication user){
        return genericGetByTwoParameter(ADD_TO_CART, listing, user.getName());
    }

    /**
     * Returns a users cart
     * @param user an authenticated user passed through the bearer token.
     * @return the users cart
     */
    @GetMapping("/get-cart")
    public ResponseEntity<Response> getCart(Authentication user){
        return genericGetByParameter(GET_CART, user.getName());
    }

    /**
     * Will remove a single listing from the users cart
     * @param listingId this is the listing to be removed
     * @param user the user
     * @return an updated cart
     */
    @GetMapping("/remove-from-cart/{id}")
    public ResponseEntity<Response> getCart(@PathVariable("id") String listingId, Authentication user){
        return genericGetByTwoParameter(REMOVE_FROM_CART, listingId, user.getName());
    }

    /**
     * Will return the userProfile used throughout the interface
     * @param user authenticated user
     * @return a UserProfile object within the Node
     */
    @GetMapping
    public ResponseEntity<Response> getUser(Authentication user){
        return genericGetByParameter(GET_USER, user.getName());
    }
    @GetMapping("/update-2fa-status/{status}")
    public ResponseEntity<Response> update2FA(@PathVariable boolean status, Authentication user){
        return genericGetByTwoParameter(UPDATE_TWO_FACTOR, user.getName(), status);
    }
}
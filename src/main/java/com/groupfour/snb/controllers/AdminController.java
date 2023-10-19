package com.groupfour.snb.controllers;

import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.UserService;
import com.groupfour.snb.utils.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>Admin Controller</h1>
 * This controller will only be accessed by the admin role
 * @author Franklin Neves Filho
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5174")
public class AdminController extends MainController{
    private UserService userService;
    private ListingService listingService;

    private final Supplier<Response> GET_ALL_USERS = () -> userService.getAll();
    private final Supplier<Response> GET_ALL_LISTINGS = () -> listingService.getAllListings();
    private final Function<String, Response> GET_LISTING = (listingId) -> listingService.getListingWithId(listingId);

    public AdminController(UserService userService, ListingService listingService){
        this.userService = userService;
        this.listingService = listingService;
    }

    @GetMapping("/get-listing/{listing_id}")
    public ResponseEntity<Response> getListingWithId(@PathVariable("listing_id")String listingId){
        return genericGetByParameter(GET_LISTING, listingId);
    }

    @GetMapping("/all-listings")
    public ResponseEntity<Response> getAllListings(){
        return genericGetAll(GET_ALL_LISTINGS);
    }

    @GetMapping("/all-users")
    public ResponseEntity<Response> getAllUsers(){
        return genericGetAll(GET_ALL_USERS);
    }
}

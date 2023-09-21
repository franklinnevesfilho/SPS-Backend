package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.UserService;
import com.groupfour.snb.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>Admin Controller</h1>
 * This controller will only be accessed by the admin role
 * @author Franklin Neves Filho
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController extends MainController{
    // will have methods and capabilities of an admin in our application
    @Autowired
    private UserService userService;
    @Autowired
    private ListingService listingService;

    final Supplier<Response> GET_ALL_USERS = () -> userService.getAll();
    final Function<String, Response> GET_LISTING = (listingId) -> listingService.getListingWithId(listingId);

    @GetMapping("/get-all-users")
    public ResponseEntity<Response> getAllUsers(){
        return genericGetAll(GET_ALL_USERS);
    }


//    @GetMapping("/get-user{user-id}")
//    public User getUserWithId(@PathVariable("user-id") String id){
//        return userService.getById(id);
//    }

//    @GetMapping("/get-all-listing/{user_id}")
//    public Iterable<Listing> getListings(@PathVariable("user_id") String id){
//        User user = userService.getById(id);
//        return user.getListings();
//    }
    @GetMapping("/get-all-listing/{user_id}-{listing_id}")
    public ResponseEntity<Response> getListingWithId(@PathVariable("listing_id")String listingId){
        return genericGetByParameter(GET_LISTING, listingId);
    }

    @GetMapping("/all-listing")
    public Iterable<Listing> getListings(){
        return listingService.getAll();
    }
}

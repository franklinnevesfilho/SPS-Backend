package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.listing.ListingRepository;
import com.groupfour.snb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>Admin Controller</h1>
 * This controller will have all the actions that can be performed by the admin role of our application
 * whether it be removing a certain listing or a specific user the admin has complete control over the application.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    // will have methods and capabilities of an admin in our application
    private final UserService userService;

    @GetMapping("/get-all-users")
    public Iterable<User> getAllUsers(){
        return userService.getAll();
    }


    @GetMapping("/get-user/{user_id}")
    public User getUserWithId(@PathVariable("user_id") String id){
        return userService.getById(id);
    }

    @GetMapping("/get-all-listing/{user_id}")
    public Iterable<Listing> getListings(@PathVariable("user_id") String id){
        User user = userService.getById(id);
        return user.getListings();
    }
    @GetMapping("/get-all-listing/{user_id}-{listing_id}")
    public Listing getListingWithId(@PathVariable("listing_id")String listingId){
        return userService.getListing(listingId);
    }

}

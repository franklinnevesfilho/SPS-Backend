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
    private final ListingRepository listingRepository;

    @GetMapping("/")
    public Iterable<User> adminController(){
        return userService.getAll();
    }


    @GetMapping("/getUser/{user_id}")
    public User getUserWithId(@PathVariable("user_id") String id){
        return userService.getById(id);
    }

    @GetMapping("/getListings")
    public Iterable<Listing> getListings(@RequestParam("user_id") String id){
        User user = userService.getById(id);
        return user.getListings();
    }

}

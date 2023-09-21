package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.CreateListing;
import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.services.ListingService;
import com.groupfour.snb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
public class UserController extends MainController{
    private final UserService userService;
    private final ListingService listingService;

//    private BiFunction<String, Object, Response> CREATE_LISTING = (userId, listing) -> listingService.addListing((CreateListing) listing, userId);

    @GetMapping()
    public String userResponse(@PathVariable String userId){
        return "User access level";
    }
    @PostMapping
    public Listing createListing(@PathVariable String userId, CreateListing listing){
        return listingService.addListing(listing, userId); //genericCreateChild(CREATE_LISTING ,userId ,listing);
    }
}

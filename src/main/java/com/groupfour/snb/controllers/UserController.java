package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Listing;
import com.groupfour.snb.models.listing.ListingCreationDTO;
import com.groupfour.snb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String userResponse(@PathVariable String userId){
        return "User access level";
    }
    @PostMapping
    public Listing createListing(@PathVariable String userId, ListingCreationDTO listing){
        return userService.addListing(userId, listing);
    }
}

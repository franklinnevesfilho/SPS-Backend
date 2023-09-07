package com.groupfour.snb.controllers;

import com.groupfour.snb.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// The all args constructor is used so Spring injects the services
@AllArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private UserService userService;

    @GetMapping("/")
    public String welcomeUser(){
        return "Welcome User";
    }
/*
    @GetMapping(path="/getAll")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping(path = "/{userId}")
    public Optional<User> getUser(@PathVariable("userId") UUID userId){
        return userService.getUser(userId);
    }
    @GetMapping(path = "/get-listings")
    public Iterable<Listing> getAllListingsFromUser(@RequestParam("userId") UUID userId){
        return userService.getAllListingsFromUser(userId);
    }
    @PostMapping(path="/addUser")
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "User: " + user.getFirstName() + " with id: " + user.getUserId() + " has been added.";
    }
 */

}

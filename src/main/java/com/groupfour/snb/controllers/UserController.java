package com.groupfour.snb.controllers;

import com.groupfour.snb.models.Listing;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// The all args constructor is used so Spring injects the services
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @PostMapping(path="/save")
    public String setUsers(){
        userService.defineUsers();
        return "Users have been added";
    }
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
        return "User with name: " + user.getFirstName() + " and id: " + user.getUserId() + " has been added.";
    }
}

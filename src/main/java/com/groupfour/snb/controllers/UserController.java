package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
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

    @PostMapping(path="/addUser")
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "User with name: " + user.getFirstName() + " and id: " + user.getUserId() + " has been added.";
    }

}

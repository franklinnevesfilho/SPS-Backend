package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.models.user.UserRole;
import com.groupfour.snb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/save")
    public String setUsers(){
        userService.defineUsers();
        return "Users have been added";
    }

    @GetMapping(path="/getAll")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{userId}")
    public Optional<User> getUser(@PathVariable("userId") Integer userId){
        return userService.getUser(userId);
    }

    @GetMapping(path="/{firstName}/{lastName}/{email}")
    public String addUser(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName,@PathVariable("email") String email){
        User user = new User(firstName,lastName,email, UserRole.BUYER);
        userService.addUser(user);
        return "User with name: " + user.getFirstName() + " and id: " + user.getUserId() + " has been added.";
    }

}

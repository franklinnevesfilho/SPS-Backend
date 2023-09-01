package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path="/save")
    public void setUsers(){
        userService.defineUsers();
    }

    @GetMapping(path="/getAll")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{userId}")
    public Optional<User> getUser(@PathVariable("userId") Integer userId){
        return userService.getUser(userId);
    }

}

package com.groupfour.snb.controllers;

import com.groupfour.snb.models.User;
import com.groupfour.snb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/")
    public Iterable<User> adminController(){
        return userService.getAll();
    }
    @GetMapping("/getUser/")
    public User getUserWithId(@RequestParam("user_id") UUID id){
        return userService.getUserById(id);
    }

}

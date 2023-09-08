package com.groupfour.snb.controllers;

import com.groupfour.snb.models.RegistrationDTO;
import com.groupfour.snb.models.User;
import com.groupfour.snb.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Authentication Controller</h1>
 * This controller will operate on the authentication process of our applicaiton.
 * The only thing that will be able to access the endpoints stated here, will be
 * the front end application using our api.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public User registerUSer(@RequestBody RegistrationDTO body){
        return authService.registerUser(body.getEmail(), body.getPassword());
    }
}

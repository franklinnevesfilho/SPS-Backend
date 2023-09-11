package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.*;
import com.groupfour.snb.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body){
        return authService.loginUser(body.getEmail(), body.getPassword());
    }
    @PostMapping("/register")
    public UserRegistrationResponseDTO registerUSer(@RequestBody UserRegistrationDTO user){
        return authService.registerUser(user);
    }
    @GetMapping("/confirm-account/")
    public String confirmAccount(@RequestParam String token){
        String result = "Please register again, session expired";

        return result;
    }
}

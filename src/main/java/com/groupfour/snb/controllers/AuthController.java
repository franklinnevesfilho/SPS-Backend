package com.groupfour.snb.controllers;

import com.groupfour.snb.models.Validator;
import com.groupfour.snb.models.tokens.RegistrationToken;
import com.groupfour.snb.models.user.DTO.UserLoginDTO;
import com.groupfour.snb.models.user.DTO.UserLoginResponseDTO;
import com.groupfour.snb.models.user.DTO.UserRegistrationDTO;
import com.groupfour.snb.models.user.DTO.UserRegistrationResponseDTO;
import com.groupfour.snb.services.AuthService;
import com.groupfour.snb.utils.Response;
import com.groupfour.snb.utils.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

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
public class AuthController extends MainController {

    @Autowired
    private AuthService authService;

    private final Function<UserLoginDTO, Response> LOGIN_USER = (body) -> authService.loginUser(body);


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLoginDTO body){
        return genericRequest(LOGIN_USER, body);
    }
    @PostMapping("/register")
    public UserRegistrationResponseDTO registerUSer(@RequestBody UserRegistrationDTO user){
        return authService.registerUser(user);
    }
    @GetMapping("/register/confirm-account")
    public RegistrationToken confirmAccount(@RequestParam String token){
        return authService.confirmAccount(token);
    }
}

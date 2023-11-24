package com.groupfour.snb.controllers;

import com.groupfour.snb.models.interfaces.Validator;
import com.groupfour.snb.models.user.DTO.UserLogin;
import com.groupfour.snb.models.user.DTO.UserRegistration;
import com.groupfour.snb.services.AuthService;
import com.groupfour.snb.utils.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

/**
 * <h1>Authentication Controller</h1>
 * This controller is responsible for every authentication process within our system. From logging in, to 2FA
 * @author Franklin Neves Filho
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends MainController {
    private AuthService authService;
    private final Function<Validator, Response> LOGIN_USER = (body) -> authService.loginUser((UserLogin) body);
    private final Function<Validator, Response> REGISTER_USER = (body) -> authService.registerUser((UserRegistration) body);
    private final Function<String, Response> CONFIRM_ACCOUNT = (body) -> authService.confirmAccount(body);
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    /**
     * @param body a UserLoginDTO
     * @return ResponseEntity<Response> UserLoginResponse
     */
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLogin body){
        return genericRequest(LOGIN_USER, body);
    }

    /**
     * @param body a UserRegistrationDTO
     * @return ResponseEntity<Response> UserRegistrationResponse
     */
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRegistration body){
        return genericRequest(REGISTER_USER, body);
    }

    /**
     * @param tokenId The registration TokenId
     * @return ResponseEntity<Response>
     */
    @GetMapping("/register/confirm-account")
    public ResponseEntity<Response> confirmAccount(@RequestParam String tokenId){
        return genericGetByParameter(CONFIRM_ACCOUNT, tokenId);
    }
}

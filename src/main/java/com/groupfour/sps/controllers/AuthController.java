package com.groupfour.sps.controllers;

import com.groupfour.sps.models.interfaces.Validator;
import com.groupfour.sps.models.user.DTO.UserLogin;
import com.groupfour.sps.models.user.DTO.UserRegistration;
import com.groupfour.sps.services.AuthService;
import com.groupfour.sps.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

/**
 * <h1>Authentication Controller</h1>
 *
 * This controller is responsible for every authentication
 * process within our system. From logging in, to 2FA
 *
 * @author Franklin Neves Filho
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends MainController {
    private AuthService authService;
    private final Function<Validator, Response> LOGIN_USER = (body) -> authService.loginUser((UserLogin) body);
    private final Function<Validator, Response> REGISTER_USER = (body) -> authService.registerUser((UserRegistration) body);
    private final Function<String, Response> CONFIRM_ACCOUNT = (body) -> authService.confirmAccount(body);
    private final Function<String, Response> CONFIRM_OTP = (otp) -> authService.confirmOTP(otp);
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
    @GetMapping("/register/confirm-account/")
    public ResponseEntity<Response> confirmAccount(@RequestParam String tokenId){
        return genericGetByParameter(CONFIRM_ACCOUNT, tokenId);
    }

    /**
     * Can only be enabled if the user is logged in and only then will start checking
     * @param otp this is a one time code that is deleted after verifying
     * @return the userProfile along with a jwt token
     */
    @GetMapping("/verify-otp/{otp}")
    public ResponseEntity<Response> confirmOTP(@PathVariable String otp){
        return genericGetByParameter(CONFIRM_OTP, otp);
    }
}

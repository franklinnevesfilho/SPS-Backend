package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.security.registration.token.RegistrationToken;
import com.groupfour.snb.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// The all args constructor is used so Spring injects the services
@AllArgsConstructor
@RestController
@RequestMapping(path="/registration")
public class RegistrationController {
    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody User request){
        return registrationService.register(request);

    }

    @GetMapping(path="/confirm")
    public RegistrationToken confirm(@RequestParam("token") UUID token){
        return registrationService.confirmToken(token);
    }
}

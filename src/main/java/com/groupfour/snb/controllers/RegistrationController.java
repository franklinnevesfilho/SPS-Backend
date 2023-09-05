package com.groupfour.snb.controllers;

import com.groupfour.snb.models.user.UserRequest;
import com.groupfour.snb.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody UserRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path="/confirm")
    public String confirm(@RequestParam("token") UUID token){
        return registrationService.confirmToken(token);
    }
}

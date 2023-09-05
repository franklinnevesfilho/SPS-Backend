package com.groupfour.snb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPage {

    @GetMapping("/verified")
    public String getVerifiedLandingPage(){
        return "Verified!";
    }
}

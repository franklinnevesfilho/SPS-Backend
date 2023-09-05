package com.groupfour.snb.services;

import com.groupfour.snb.models.user.UserRequest;
import com.groupfour.snb.security.registration.token.RegistrationToken;
import com.groupfour.snb.security.registration.token.RegistrationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {
    private RegistrationTokenService tokenService;
    private EmailService emailService;
    private UserService userService;


    public String register(UserRequest request) {
        String result;
        if(isValidEmail(request.getEmail())){
            result = "passed";
            String token = UUID.randomUUID().toString();

            String link = "http://localhost:8080/registration/confirm";



        }else{
            result = "failed";
        }
        return result;
    }

    private boolean isValidEmail(String email){
        //TODO: email verification
        return true;
    }

    public String confirmToken(UUID token) {
        RegistrationToken confirmedToken = tokenService.getToken(token);
        if(confirmedToken.getConfirmedAt() != null){
            //TODO: handle registration exceptions
            throw new IllegalStateException("already confirmed");

        }else if(confirmedToken.getConfirmedAt().isBefore(LocalDateTime.now())){
            // If the expiration is before now... it has already expired
             throw new IllegalStateException("Token expired");
        }else{
            confirmedToken.setConfirmedAt(LocalDateTime.now());
            userService.activateUser(confirmedToken.getUser());
        }
        return "confirmed";
    }
}
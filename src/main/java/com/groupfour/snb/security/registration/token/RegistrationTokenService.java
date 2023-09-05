package com.groupfour.snb.security.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository tokenRepo;

    public void saveToken(RegistrationToken token){
        tokenRepo.save(token);
    }
    public int setConfirmedAt(String token){
        return tokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }

    public RegistrationToken getToken(UUID token) {
        return tokenRepo.getReferenceById(token);
    }
}

package com.groupfour.snb.security.registration.token;

import com.groupfour.snb.models.user.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@NoArgsConstructor
@Service
public class RegistrationTokenService {
    @Autowired
    private RegistrationTokenRepository tokenRepo;

    public void saveToken(RegistrationToken token){
        tokenRepo.save(token);
    }
    public String setConfirmedAt(String token){
        Optional<RegistrationToken> foundToken = tokenRepo.findById(UUID.fromString(token));
        foundToken.ifPresent(registrationToken -> registrationToken.setConfirmedAt(LocalDateTime.now()));
        return "User Confirmed";
    }

    public Optional<RegistrationToken> getToken(UUID token) {
        return tokenRepo.findById(token);
    }

    public RegistrationToken createToken(User user) {
        RegistrationToken newToken = new RegistrationToken();
        newToken.setUser(user);
        tokenRepo.save(newToken);
        return newToken;
    }
}

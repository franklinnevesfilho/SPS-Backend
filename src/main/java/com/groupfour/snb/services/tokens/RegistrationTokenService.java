package com.groupfour.snb.services.tokens;

import com.groupfour.snb.models.tokens.RegistrationToken;
import com.groupfour.snb.models.tokens.RegistrationTokenDTO;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.tokens.RegistrationTokenRepository;
import com.groupfour.snb.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RegistrationTokenService {
    private final RegistrationTokenRepository tokenRepository;
    private final EmailService emailService;

    public void saveToken(RegistrationToken token){
        tokenRepository.save(token);
    }

    public String confirmToken(String id){
        tokenRepository.findById(id).ifPresent(token ->{
            token.setConfirmedAt(LocalDateTime.now());
        });
        return "User Confirmed";
    }

    public RegistrationToken getToken(String id) {
        return tokenRepository.findById(id).orElseThrow();
    }


    public RegistrationTokenDTO register(User user) {
        RegistrationToken token = tokenRepository.save(RegistrationToken.builder()
                .user(user)
                .build());
        emailService.sendVerificationEmail(token.getId(), user);

        return RegistrationTokenDTO.builder()
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .id(token.getId()).build();
    }
}


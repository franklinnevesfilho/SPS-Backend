package com.groupfour.snb.utils.tokens;

import com.groupfour.snb.models.tokens.RegistrationToken;
import com.groupfour.snb.models.tokens.RegistrationTokenDTO;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.models.user.DTO.UserRegistrationDTO;
import com.groupfour.snb.repositories.tokens.RegistrationTokenRepository;
import com.groupfour.snb.utils.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@ComponentScan
public class RegistrationTokenUtil {
    private final RegistrationTokenRepository tokenRepository;
    private final EmailUtil emailUtil;

    public void confirmToken(String id){
        tokenRepository.findById(id).ifPresent(token -> {
            if(token.getConfirmedAt() == null && !token.isExpired()) {
                token.setConfirmedAt(LocalDateTime.now());
                token.getUser().setEnabled(true);
                tokenRepository.save(token);
            }else{
                tokenRepository.deleteById(id);
            }
        });
    }

    public RegistrationTokenDTO register(User user) {
        RegistrationToken token = tokenRepository.save(RegistrationToken.builder()
                .user(user)
                .build());
        emailUtil.sendVerificationEmail(
                token.getId(),
                UserRegistrationDTO.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build());

        return RegistrationTokenDTO.builder()
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .id(token.getId()).build();
    }

    public RegistrationToken getToken(String token) {
        return tokenRepository.findById(token).orElseThrow();
    }

    public Iterable<RegistrationToken> getAll() {
        return tokenRepository.findAll();
    }

    public void delete(RegistrationToken token) {
        tokenRepository.delete(token);
    }
}


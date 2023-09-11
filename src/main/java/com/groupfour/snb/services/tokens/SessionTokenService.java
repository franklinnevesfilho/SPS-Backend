package com.groupfour.snb.services.tokens;

import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.tokens.SessionTokenRepository;
import com.groupfour.snb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SessionTokenService {
    private final SessionTokenRepository tokenRepository;
    private final UserService userService;

    public SessionToken createToken(User user) {
        return tokenRepository.save(
                SessionToken.builder()
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build());
    }
}

package com.groupfour.snb.utils.tokens;

import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.tokens.SessionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SessionTokenUtil {
    private final SessionTokenRepository tokenRepository;

    public SessionToken createToken(User user) {
        return tokenRepository.save(
                SessionToken.builder()
                        .user(user)
                        .build());
    }

    public boolean hasSession(String userId){
        return tokenRepository.findByUserId(userId).isPresent();
    }
    public boolean sessionHasExpired(String userId){
        boolean result = false;
       Optional<SessionToken> token = tokenRepository.findByUserId(userId);
        if(token.isPresent()){
            result = token.get().getExpiresAt().isBefore(LocalDateTime.now());
            if(result) tokenRepository.delete(token.get());
        }
        return result;
    }

    public SessionToken currentSession(String userId) {
        return tokenRepository.findByUserId(userId).orElseThrow(()-> new UsernameNotFoundException("Session Token not found for User with Id: " + userId));
    }

    public Iterable<SessionToken> getAll() {
        return tokenRepository.findAll();
    }

    public void delete(SessionToken token) {
        tokenRepository.delete(token);
    }
}

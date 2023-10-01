package com.groupfour.snb.utils.security;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import lombok.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Builder
@Data
@Component
public class AuthProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = User.builder().build();
        boolean result = false;
        // Is this authenticated obj an AuthUser
        String email = authentication.getDetails().toString();
        String password = authentication.getCredentials().toString();
        //Find user in repo
        Optional<User> foundUser = userRepository.findByEmail(email);
        //Does user exist?
        if(foundUser.isPresent()){
            user = foundUser.get();
            // Do passwords match?
            if(passwordEncoder.matches(password, user.getPassword())){
                result = true;
            }
        }
        return SecurityUser.builder()
                .user(user)
                .authenticated(result)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SecurityUser.class);
    }
}

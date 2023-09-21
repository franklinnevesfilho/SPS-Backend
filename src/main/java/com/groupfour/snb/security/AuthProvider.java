package com.groupfour.snb.security;

import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import lombok.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class AuthProvider implements AuthenticationProvider {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        SecurityUser securityUser = SecurityUser.builder().build();
        boolean result = false;
        // Is this authenticated obj an AuthUser
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        //Find user in repo
        Optional<User> user = userRepository.findByEmail(email);
        //Does user exist?
        if(user.isPresent()){
            securityUser.setUser(user.get());
            // Do passwords match?
            if(passwordEncoder.matches(password, user.get().getPassword())){
                result = true;
            }
        }
        securityUser.setAuthenticated(result);
        return securityUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SecurityUser.class);
    }
}

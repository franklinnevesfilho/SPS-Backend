package com.groupfour.snb.services;

import com.groupfour.snb.models.*;
import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.*;
import com.groupfour.snb.services.tokens.RegistrationTokenService;
import com.groupfour.snb.services.tokens.SessionTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final RegistrationTokenService registrationService;
    private final SessionTokenService sessionService;

    public UserRegistrationResponseDTO registerUser(UserRegistrationDTO userDTO){

        Role userRole = roleService.getRoleByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .authorities(authorities)
                .listings(new LinkedList<>())
                .purchasedListings(new LinkedList<>())
                .build();
        userService.add(user);
        return UserRegistrationResponseDTO.builder()
                .user(userDTO)
                .registrationToken(registrationService.register(user))
                .build();
    }

    public UserLoginResponseDTO loginUser(String email, String password) {
        if(authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)).isAuthenticated()) {
            SessionToken token = sessionService.createToken(userService.getByEmail(email));
            return UserLoginResponseDTO.builder()
                    .user(UserLoginDTO.builder().email(email).password(password).build())
                    .sessionToken(token)
                    .build();
        }
        else return UserLoginResponseDTO.builder().build();
    }
}

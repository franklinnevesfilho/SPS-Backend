package com.groupfour.snb.services;

import com.groupfour.snb.models.*;
import com.groupfour.snb.models.tokens.RegistrationToken;
import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.*;
import com.groupfour.snb.models.user.DTO.UserLoginDTO;
import com.groupfour.snb.models.user.DTO.UserLoginResponseDTO;
import com.groupfour.snb.models.user.DTO.UserRegistrationDTO;
import com.groupfour.snb.models.user.DTO.UserRegistrationResponseDTO;
import com.groupfour.snb.utils.tokens.RegistrationTokenUtil;
import com.groupfour.snb.utils.tokens.SessionTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final RegistrationTokenUtil registrationService;
    private final SessionTokenUtil sessionService;

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
            User user = userService.getByEmail(email);
            SessionToken token;
            if(!sessionService.hasSession(user.getId())){
                 token = sessionService.createToken(user);
            }else if (sessionService.sessionHasExpired(user.getId())){
                token = sessionService.createToken(user);
            }else{
                token = sessionService.currentSession(user.getId());
            }

            return UserLoginResponseDTO.builder()
                    .user(UserLoginDTO.builder().email(email).password(password).build())
                    .sessionToken(token)
                    .build();
        }
        else{
            log.warn("user was not authenticated");
            return UserLoginResponseDTO.builder().build();
        }
    }

    public RegistrationToken confirmAccount(String token) {
        registrationService.confirmToken(token);
        return registrationService.getToken(token);
    }
}

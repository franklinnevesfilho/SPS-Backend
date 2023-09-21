package com.groupfour.snb.services;

import com.groupfour.snb.models.*;
import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.*;
import com.groupfour.snb.models.user.DTO.UserLogin;
import com.groupfour.snb.models.user.DTO.UserLoginResponse;
import com.groupfour.snb.models.user.DTO.UserRegistration;
import com.groupfour.snb.repositories.UserRepository;
import com.groupfour.snb.security.SecurityUser;
import com.groupfour.snb.utils.responses.Response;
import com.groupfour.snb.utils.tokens.RegistrationTokenService;
import com.groupfour.snb.utils.tokens.SessionTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService extends MainService{
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RegistrationTokenService registrationService;
    private final SessionTokenUtil sessionService;

    public Response registerUser(UserRegistration registerUser){
        // Authorities list for users
        Set<Role> authorities = new HashSet<>();

        //Todo: retrieve what role you want to assign the user
        Role userRole = roleService.getRoleByAuthority("USER");
        authorities.add(userRole);
        User user = User.builder()
                .firstName(registerUser.getFirstName())
                .lastName(registerUser.getLastName())
                .email(registerUser.getEmail())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .authorities(authorities)
                .build();

        return registrationService.register(userRepository.save(user));
    }
    public Response loginUser(UserLogin userLogin) {
        UserLoginResponse userLResponse = UserLoginResponse.builder().build();
        Optional<User> foundUser = userRepository.findByEmail(userLogin.getEmail());
        Response response = Response.builder().build();
        List<String> errors = new LinkedList<>();
        if(foundUser.isPresent() && foundUser.get().isEnabled()) {

            SecurityUser secUser = new SecurityUser(User.builder()
                    .id(foundUser.get().getId())
                    .email(userLogin.getEmail())
                    .password(userLogin.getPassword())
                    .build(),
                    false);
            if (authManager.authenticate(secUser).isAuthenticated()) {
                // Session Manager
                SessionToken token;
                if (!sessionService.hasSession(foundUser.get().getId())) {
                    token = sessionService.createToken(foundUser.get());
                } else if (sessionService.sessionHasExpired(foundUser.get().getId())) {
                    token = sessionService.createToken(foundUser.get());
                } else {
                    token = sessionService.currentSession(foundUser.get().getId());
                }

                userLResponse.setUser(foundUser.get());
                userLResponse.setSessionToken(token);
            } else {
                log.warn("user was not authenticated");
                errors.add("user password is wrong");
            }
        }
        else {
            errors.add("User with Email: "+ userLogin.getEmail() + " not found... Please activate account via email before logging in");
        }
        response.setNode(mapToJson(userLResponse));
        response.setErrors(errors);
        return response;
    }

    public Response confirmAccount(String token) {

        return registrationService.confirmToken(token);
    }
}

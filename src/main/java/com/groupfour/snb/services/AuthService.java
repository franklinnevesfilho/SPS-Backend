package com.groupfour.snb.services;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.groupfour.snb.models.*;
import com.groupfour.snb.models.tokens.SessionToken;
import com.groupfour.snb.models.user.*;
import com.groupfour.snb.models.user.DTO.UserLogin;
import com.groupfour.snb.models.user.DTO.UserLoginResponse;
import com.groupfour.snb.models.user.DTO.UserRegistration;
import com.groupfour.snb.utils.Response;
import com.groupfour.snb.utils.tokens.RegistrationTokenService;
import com.groupfour.snb.utils.tokens.SessionTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService extends MainService{
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final RegistrationTokenService registrationService;
    private final SessionTokenUtil sessionService;

    public Response registerUser(UserRegistration user){
        mapper.registerModule(new JavaTimeModule());
        List<String> errors = new LinkedList<>();

        Role userRole = roleService.getRoleByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);


        //TODO: make sure email does not exist before adding user
//        if(){
//
//        }
        User gUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .authorities(authorities)
                .listings(new LinkedList<>())
                .purchasedListings(new LinkedList<>())
                .build();
        userService.add(gUser);

        return Response.builder()
                .node(mapToJson(gUser))
                .build();
    }

    public Response loginUser(UserLogin userLogin) {

        Response response;
        UserLoginResponse userResponse = UserLoginResponse.builder()
                .user(UserLogin.builder().email(userLogin.getEmail()).password(userLogin.getPassword()).build())
                .build();


        if(authManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())).isAuthenticated()) {
            User user = userService.getByEmail(userLogin.getEmail());
            SessionToken token;
            if(!sessionService.hasSession(user.getId())){
                 token = sessionService.createToken(user);
            }else if (sessionService.sessionHasExpired(user.getId())){
                token = sessionService.createToken(user);
            }else{
                token = sessionService.currentSession(user.getId());
            }

            userResponse.setSessionToken(token);

            response = Response.builder()
                    .node(mapToJson(userResponse))
                    .build();
        }
        else{

            log.warn("user was not authenticated");
            List<String> errors = new LinkedList<>();

            errors.add("user was not authenticated");
            response = Response.builder()
                    .errors(errors)
                    .build();
        }

        return response;
    }

    public Response confirmAccount(String token) {
        return registrationService.confirmToken(token);
    }
}

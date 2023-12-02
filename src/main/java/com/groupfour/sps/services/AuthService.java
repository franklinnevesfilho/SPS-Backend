package com.groupfour.sps.services;

import com.groupfour.sps.models.tokens.TwoFactorToken;
import com.groupfour.sps.models.user.*;
import com.groupfour.sps.models.user.DTO.UserLogin;
import com.groupfour.sps.models.user.DTO.UserLoginResponse;
import com.groupfour.sps.models.user.DTO.UserRegistration;
import com.groupfour.sps.repositories.TwoFactorRepository;
import com.groupfour.sps.repositories.UserRepository;
import com.groupfour.sps.utils.email.EmailUtil;
import com.groupfour.sps.utils.security.AuthProvider;
import com.groupfour.sps.models.user.SecurityUser;
import com.groupfour.sps.models.responses.Response;
import com.groupfour.sps.utils.security.tokens.OTPTokenGenerator;
import com.groupfour.sps.utils.security.tokens.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * <h1>AuthService</h1>
 * This class handles all operations for authentication and registration
 * @author Franklin Neves Filho
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService extends MainService{

    private final AuthProvider loginManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RegistrationTokenService registrationService;
    private final JwtTokenService jwtTokenService;
    private final TwoFactorRepository twoFactorRepository;
    private final EmailUtil emailService;

    private final static String LOGIN_ERROR = "Email or password have been inputted incorrectly.";
    /**
     * This method is called once the user has entered their info and is ready to confirm their account
     * @param registerUser A DTO consisting of firstname, lastname, email and password.
     * @return A response saying the email, to activate the account, has been sent successfully.
     */
    public Response registerUser(UserRegistration registerUser){
        // Authorities list for users
        Set<Role> authorities = new HashSet<>();
        //Todo: retrieve what role you want to assign the user
        Role userRole = roleService.getRoleByAuthority("USER");
        authorities.add(userRole);
        log.info("Email saved: " + registerUser.getEmail());
        log.info("Password: " + registerUser.getPassword());
        return registrationService.registerUser(
                userRepository.save(
                        User.builder()
                                .firstName(registerUser.getFirstName())
                                .lastName(registerUser.getLastName())
                                .email(registerUser.getEmail())
                                .password(passwordEncoder.encode(registerUser.getPassword()))
                                .authorities(authorities)
                                .build()
                ));
    }
    /***
     * This method is called when a user would like to log-in.
     * It generated a session token used for every other http call to ensure the users credentials.
     * It creates a new SecurityUser using the id from the foundUser and the credentials from the userLogin
     * and authenticates the user.
     * @param userLogin This is a DTO consisting of only email and password.
     * @return This will return a response either the logged-in user or a list of errors
     */
    public Response loginUser(UserLogin userLogin) {
        Optional<User> foundUser = userRepository.findByEmail(userLogin.getEmail());
        List<String> errors = new LinkedList<>();
        UserLoginResponse user = null;

        if(foundUser.isPresent() && foundUser.get().isEnabled()) {
            String jwtToken;
             SecurityUser secUser = SecurityUser.builder()
                     .user(User.builder()
                             .id(foundUser.get().getId())
                             .email(userLogin.getEmail())
                             .password(userLogin.getPassword())
                             .build())
                     .build();
            if (loginManager.authenticate(secUser).isAuthenticated()) {
                secUser = SecurityUser.builder().user(foundUser.get()).build();

                jwtToken = jwtTokenService.generateJwt(secUser);

                user = UserLoginResponse.builder()
                        .firstName(foundUser.get().getFirstName())
                        .lastName(foundUser.get().getLastName())
                        .twoFactorEnabled(foundUser.get().isTwoFactorEnabled())
                        .jwt(jwtToken)
                        .build();

                if(foundUser.get().isTwoFactorEnabled()){
                    String twoAuth = OTPTokenGenerator.generateToken();
                    TwoFactorToken twoFactorToken = TwoFactorToken.builder()
                            .token(twoAuth)
                            .jwt(jwtToken)
                            .build();
                    twoFactorRepository.save(twoFactorToken);
                    log.info(twoAuth);
                    emailService.sendTwoFactorEmail(twoFactorToken, foundUser.get().getEmail());
                }else{
                    log.info("no otp token");
                }

            } else {
                log.warn("Not authenticated");
                errors.add(LOGIN_ERROR);
            }
        }
        else {
            log.info("no user found");
            errors.add(LOGIN_ERROR);
        }
        return Response.builder()
                .node(mapToJson(user))
                .errors(errors)
                .build();
    }

    /**
     * @param token The registration tokenId is passed and confirmed
     * @return a response with the userRegistrationResponse in the node
     */
    public Response confirmAccount(String token) {
        return registrationService.verifyRegistrationToken(token);
    }

    public Response confirmOTP(String otp) {
        Optional<TwoFactorToken> token = twoFactorRepository.findByToken(otp);
        String response = "";
        List<String> errors = new LinkedList<>();
        if(token.isPresent()) {
            response = token.get().getJwt();
            twoFactorRepository.delete(token.get());
        }else{
            errors.add("OTP INVALID");
        }
        return Response.builder()
                .node(mapToJson(response))
                .errors(errors)
                .build();
    }
}

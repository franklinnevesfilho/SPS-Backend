package com.groupfour.snb.utils.tokens;

import com.groupfour.snb.models.tokens.RegistrationToken;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.tokens.RegistrationTokenRepository;
import com.groupfour.snb.services.MainService;
import com.groupfour.snb.utils.responses.Response;
import com.groupfour.snb.utils.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@ComponentScan
public class RegistrationTokenService extends MainService {
    private final RegistrationTokenRepository tokenRepository;
    private final EmailUtil emailService;

    public Response confirmToken(String id){
        List<String> errors = new LinkedList<>();
        Optional<RegistrationToken> token = tokenRepository.findById(id);

        if(token.isPresent()){
            RegistrationToken registeredToken = token.get();

            if(registeredToken.getConfirmedAt() == null && !registeredToken.isExpired()) {
                registeredToken.setConfirmedAt(LocalDateTime.now());
                registeredToken.getUser().setEnabled(true);
                tokenRepository.save(registeredToken);
            }else{
                tokenRepository.deleteById(id);
                errors.add("Registration token has either been confirmed or is expired please register again");
            }
        }else{
            errors.add("Registration token is not available");
        }

        return Response.builder()
                .node(mapToJson(token))
                .errors(errors)
                .build();
    }

    public Response register(User user) {
        List<String> errors = new LinkedList<>();

        RegistrationToken token;
        Optional<RegistrationToken> foundToken = tokenRepository.findByUserId(user.getId());
        if(foundToken.isPresent() && !foundToken.get().isExpired()){
            errors.add("This user already has a registration token");
            token = foundToken.get();
        }else if(foundToken.isPresent() && foundToken.get().isExpired()){
            errors.add("Registration token has expired... Generated a new one");
            token = tokenRepository.save(RegistrationToken.builder()
                    .user(user)
                    .build());
            // Resend email
            emailService.sendVerificationEmail(token.getId(),user);
        }else{
            // If token never existed
            token = tokenRepository.save(RegistrationToken.builder()
                    .user(user)
                    .build());
            emailService.sendVerificationEmail(token.getId(),user);
        }

        return Response.builder()
                .node(mapToJson(token))
                .errors(errors)
                .build();
    }

    public Iterable<RegistrationToken> getAll() {
        return tokenRepository.findAll();
    }

    public void delete(RegistrationToken token) {
        tokenRepository.delete(token);
    }

}


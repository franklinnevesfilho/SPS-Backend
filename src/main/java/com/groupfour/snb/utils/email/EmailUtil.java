package com.groupfour.snb.utils.email;

import com.groupfour.snb.models.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Service
public class EmailUtil extends EmailGeneratorUtil{

    public EmailUtil(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void sendVerificationEmail(String tokenId, User user){
        String link = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/confirm-account")
                        .queryParam("tokenId",tokenId)
                        .toUriString();

        generateHttpMessage(generateVerificationEmail(link, user), user.getEmail(), "Email Verification");
        getMailSender().send(
                generateHttpMessage(generateVerificationEmail(link, user), user.getEmail(), "Email Verification")
        );
        log.info("Verification Sent");
    }

    private String generateVerificationEmail(String link, User user){
        String withName = VERIFICATION_EMAIL.replace("**name", user.getFirstName()+ " " + user.getLastName());
        return withName.replace("**link",link);
    }
}

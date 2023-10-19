package com.groupfour.snb.utils.email;

import com.groupfour.snb.models.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@Slf4j
@Service
public class EmailUtil{
    @Autowired
    private EmailGeneratorUtil emailGenerator;
    private static final String VERIFICATION_EMAIL = "registrationEmail";

    public void sendVerificationEmail(String tokenId, User user){
        String link = ServletUriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("5174")
                .path("/confirm-account/" + tokenId)
                .toUriString();
        emailGenerator.context.setVariable("url_link", link);
        String body = emailGenerator.getTemplateEngine().process(VERIFICATION_EMAIL, emailGenerator.context);
        emailGenerator.sendMessage(body, user.getEmail(), "Activate your Account");
        log.info("Verification Sent");
    }
}

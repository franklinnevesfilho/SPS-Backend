package com.groupfour.sps.utils.email;

import com.groupfour.sps.models.tokens.TwoFactorToken;
import com.groupfour.sps.models.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Service
public class EmailUtil{
    private final EmailGeneratorUtil emailGenerator;
    private static final String VERIFICATION_EMAIL = "registrationEmail";
    private static final String OTP_EMAIL = "OTPEmail";

    public EmailUtil(EmailGeneratorUtil emailGenerator){
        this.emailGenerator = emailGenerator;
    }

    public void sendVerificationEmail(String tokenId, User user){
        String link = ServletUriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("5173")
                .path("/confirm-account/" + tokenId)
                .toUriString();
        emailGenerator.context.setVariable("url_link", link);
        String body = emailGenerator.getTemplateEngine().process(VERIFICATION_EMAIL, emailGenerator.context);
        emailGenerator.sendMessage(body, user.getEmail(), "Activate your Account");
        log.info("Verification Sent");
    }

    public void sendOTPEmail(TwoFactorToken token, String email){
        emailGenerator.context.setVariable("OTP", token.getToken());
        String body = emailGenerator.getTemplateEngine().process(OTP_EMAIL, emailGenerator.context);
        emailGenerator.sendMessage(body, email, "One Time passcode");
        log.info("OTP sent for user with email: "+ email);
    }
}

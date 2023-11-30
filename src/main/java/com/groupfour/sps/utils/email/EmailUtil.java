package com.groupfour.sps.utils.email;

import com.groupfour.sps.models.tokens.TwoFactorToken;
import com.groupfour.sps.models.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Franklin Neves
 */
@Slf4j
@Service
public class EmailUtil {
    private final EmailGeneratorUtil emailGenerator;
    private static final String VERIFICATION_EMAIL_TEMPLATE = "registrationEmail";
    private static final String TWO_FACTOR_EMAIL_TEMPLATE = "OTPEmail";

    public EmailUtil(EmailGeneratorUtil emailGenerator) {
        this.emailGenerator = emailGenerator;
    }

    public void sendVerificationEmail(String tokenId, User user) {
        String link = buildVerificationLink(tokenId);
        emailGenerator.context.setVariable("url_link", link);
        String body = emailGenerator.getTemplateEngine().process(VERIFICATION_EMAIL_TEMPLATE, emailGenerator.context);
        sendEmail(body, user.getEmail(), "Activate your Account", "Verification Sent");
    }

    public void sendTwoFactorEmail(TwoFactorToken token, String email) {
        emailGenerator.context.setVariable("two_factor", token.getToken());
        String body = emailGenerator.getTemplateEngine().process(TWO_FACTOR_EMAIL_TEMPLATE, emailGenerator.context);
        sendEmail(body, email, "One Time passcode", "Two factor sent for user with email: " + email);
    }

    private String buildVerificationLink(String tokenId) {
        return ServletUriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("5173")
                .path("/confirm-account/" + tokenId)
                .toUriString();
    }

    private void sendEmail(String body, String recipientEmail, String subject, String logMessage) {
        emailGenerator.sendMessage(body, recipientEmail, subject);
        log.info(logMessage);
    }
}

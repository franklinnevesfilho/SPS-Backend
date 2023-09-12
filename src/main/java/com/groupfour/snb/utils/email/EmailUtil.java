package com.groupfour.snb.utils.email;

import com.groupfour.snb.models.user.DTO.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class EmailUtil {
    private final JavaMailSender mailSender;
    private static final String VERIFICATION_EMAIL =
            "<body>\n"+
                    " <p>This activation link belongs to **name, and will expire in 20 minutes<p>"+
                    "<h3><a href= **link >Please click here to activate account<a><h3>" +
            "</body>";
    public void sendVerificationEmail(String token, UserRegistrationDTO user){
        String link = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/confirm-account")
                        .queryParam("token",token)
                        .toUriString();

        new EmailGeneratorUtil(mailSender, "Email Verification", user.getEmail())
                .sendHttpEmail(generateVerificationEmail(link, user));
    }

    private String generateVerificationEmail(String link, UserRegistrationDTO user){
        String withName = VERIFICATION_EMAIL.replace("**name", user.getFirstName()+ " " + user.getLastName());
        return withName.replace("**link",link);
    }
}

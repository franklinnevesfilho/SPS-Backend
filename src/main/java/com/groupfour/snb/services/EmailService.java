package com.groupfour.snb.services;

import com.groupfour.snb.utils.EmailGeneratorUtil;
import com.groupfour.snb.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String token, User request){
        String link =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/confirm").queryParam("token",token).toUriString();
        String body =
                "<body>\n"+
                        " <p>This is a test email. Token: "+ token + "<p>\n"+
                        "<h3><a href=\""+ link +"\">Verify<a><h3>" +
                        "</body>";
       new EmailGeneratorUtil(mailSender,"Email Verification", request.getUsername()).sendHttpEmail(body);

    }
}

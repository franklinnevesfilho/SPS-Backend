package com.groupfour.snb.services;

import com.groupfour.snb.models.email.EmailGenerator;
import com.groupfour.snb.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user){
        String body =
                """
                        <body>
                        <p>This is a test email</p>
                        </body>
                        """;
       new EmailGenerator(mailSender,"Email Verification", user.getEmail()).sendHttpEmail(body);

    }
}

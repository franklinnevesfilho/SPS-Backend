package com.groupfour.snb.services;

import com.groupfour.snb.models.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {

        // We use MimiMessage to create an HTTP email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(toEmail);
        helper.setText(body, true);
        helper.setSubject(subject);

        mailSender.send(message);
    }

    public void sendVerificationEmail(User user) throws MessagingException {
        String body = "<p> Please click the link below to verify account </p>";
        body += "<h3><a href=\"http://localhost:8080/verified\">Verify<a><h3>";


        sendEmail(user.getEmail(),"Students & Books verification", body);
    }
}

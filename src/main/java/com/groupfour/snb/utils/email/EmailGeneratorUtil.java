package com.groupfour.snb.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Email Generator</h1>
 * This class will be responsible for generating all types of emails.
 * From forgot password request to activating the account via email.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@Slf4j
@AllArgsConstructor
@Data
public class EmailGeneratorUtil {
    private JavaMailSender mailSender;
    private final String emailSubject;
    private final String toEmail;

    @Async
    public void sendHttpEmail(String http){
        mailSender.send(generateHttpMessage(http));
    }

    private MimeMessage generateHttpMessage(String body) {
        // We use MimeMessage to create an HTTP email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(this.getToEmail());
            helper.setText(body, true);
            helper.setSubject(getEmailSubject());
        }
        catch (MessagingException e) {
            log.warn("Failed to generate email");
        }
        return message;
    }
}

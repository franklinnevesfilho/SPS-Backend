package com.groupfour.sps.utils.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * <h1>Email Generator</h1>
 * This class will be responsible for generating all types of emails.
 * From forgot password request to activating the account via email.
 *
 * @author Franklin Neves Filho
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Data
public class EmailGeneratorUtil {

    protected Context context = new Context();
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    protected void sendMessage(String body, String toEmail, String subject){
        MimeMessage message = generateHttpMessage(body, toEmail, subject);
        mailSender.send(message);
    }

    private MimeMessage generateHttpMessage(String body, String toEmail, String subject) {
        // We use MimeMessage to create an HTTP email
        MimeMessage message = getMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toEmail);
            helper.setText(body, true);
            helper.setSubject(subject);
        }
        catch (MessagingException e) {
            log.warn("Failed to generate email");
        }
        return message;
    }

    protected MimeMessage getMimeMessage(){
        return mailSender.createMimeMessage();
    }

}

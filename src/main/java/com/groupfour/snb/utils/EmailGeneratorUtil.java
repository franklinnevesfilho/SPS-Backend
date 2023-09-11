package com.groupfour.snb.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
@AllArgsConstructor
public class EmailGeneratorUtil {
    private JavaMailSender mailSender;
    private final String emailSubject;
    private final String toEmail;
    private final Logger mailLog = Logger.getLogger(EmailGeneratorUtil.class.getName());

    public String getEmailSubject(){
        return this.emailSubject;
    }
    public String getToEmail(){
        return this.toEmail;
    }

    @Async
    public void sendHttpEmail(String body){
        mailSender.send(generateHttpMessage(body));
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
            mailLog.log(Level.WARNING, "Failed to generate email");
        }
        return message;
    }
}

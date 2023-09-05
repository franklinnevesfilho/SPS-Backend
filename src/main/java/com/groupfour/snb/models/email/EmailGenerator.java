package com.groupfour.snb.models.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class EmailGenerator {
    private JavaMailSender mailSender;
    private final String emailSubject;
    private final String toEmail;
    private final Logger mailLog = Logger.getLogger(EmailGenerator.class.getName());

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

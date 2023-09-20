package com.groupfour.snb.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

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
    protected static final String VERIFICATION_EMAIL =
            "<body>\n"+
                    " <p>This activation link belongs to **name, and will expire in 20 minutes<p>"+
                    "<h3><a href= **link >Please click here to activate account<a><h3>" +
                    "</body>";
    private JavaMailSender mailSender;

    @Async
    protected MimeMessage generateHttpMessage(String body, String toEmail, String subject) {
        // We use MimeMessage to create an HTTP email
        MimeMessage message = mailSender.createMimeMessage();
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
}

package com.example.Registration.EmailVerification.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class EmailService implements EmailSender {


    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);//will log you into your email
    private final JavaMailSender mailSender;// mail sender method


    @Override
    @Async
    public void send(String to, String email) {

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("ikymulla@gmail.com");
            mailSender.send(mimeMessage);//send the email

        }
        catch (MessagingException e) {
            LOGGER.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new IllegalStateException("Failed to send email", e);
        }

    }
}

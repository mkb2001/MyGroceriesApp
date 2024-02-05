package com.example.groceries.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.groceries.security.dto.UserPasswordReset;

import java.io.UnsupportedEncodingException;

@Component
@Configuration
@Data
@RequiredArgsConstructor
public class PasswordResetEmail {
    private final JavaMailSender mailSender;

    public JavaMailSender sendPasswordResetEmail(UserPasswordReset theUser, String token)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "My Groceries Email Service";
        String mailContent = "<html>" +
                "<head>" +
                "<style>" +
                ".container { max-width: 100px; margin: 0 auto; }" +
                ".copy-button { display: inline-block; padding: 8px 16px; background-color: #4CAF50; color: white; text-decoration: none; cursor: pointer; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p> Hi " + theUser.getUserName() + ", </p>" +
                "<p><b>You recently requested to reset your password!</b></p>" +
                "<p>Please, copy the token below and paste in the app to complete the action. This passcode will last for 5 minutes!</p>" +
                "<p>Your JWT Token: </p>" +
                "<div><p><b>"+ token +"</b></p></div>"+
                "</div>" +
                "</body>" +
                "</html>";
        
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("andrewkah2022@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
        return mailSender;
    }
}


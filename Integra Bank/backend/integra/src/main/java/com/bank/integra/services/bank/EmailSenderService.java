package com.bank.integra.services.bank;

import com.bank.integra.services.security.token.PasswordResetTokenService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordResetTokenService resetTokenService;

    //TODO сделать универсальный метод отправки, например, сделать енум, в котором предусмотрится сообщение с паролем, баном, логином и тп
    public void sendPasswordResetConfirmation(String toEmail) {
        try {
            String resetLink = generateResetLink(toEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setTo(toEmail);
            messageHelper.setSubject("Password change confirmation.");
            messageHelper.setText(buildEmailBody(resetLink), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить письмо: " + e.getMessage());
        }
    }

    private String generateResetLink(String email) {
        String token = resetTokenService.createResetTokenForUser(email.trim());

        //TODO URLA МЕНЯТЬ!
        String baseUrl = "http://localhost:8080";
        String resetLink = baseUrl + "/user/reset-password?token=" + token;
        return resetLink;
    }

    private String buildEmailBody(String resetLink) {
        return """
            <h2>Password change</h2>
            <p>Somebody requested the change of your password.</p>
            <p>The link will be active for the next 15 minutes.</p>
            <p>To confirm, please click the button below:</p>
            <a href="%s" style="display:inline-block;background-color:#4CAF50;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;">Confirm change</a>
            <p>If you didn't request the change, please ignore the letter.</p>
        """.formatted(resetLink);
    }

}

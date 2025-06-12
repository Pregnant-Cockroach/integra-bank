package com.bank.integra.services.security.token;

import com.bank.integra.dao.ResetPasswordTokenRepository;
import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.entities.person.User;
import com.bank.integra.entities.token.PasswordResetToken;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    @Autowired
    private ResetPasswordTokenRepository passwordTokenRepository;

    @Autowired
    private UserService userService;

    public String createResetTokenForUser(String email) {
        UserDetails userDetails = userService.getUserDetailsByEmail(email);
        User user = userService.getUserById(userDetails.getUserId());
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        resetToken.setUsed(false);

        passwordTokenRepository.save(resetToken);

        return token;
    }

    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token).orElse(null);
    }

    public PasswordResetToken save(PasswordResetToken token) {
        return passwordTokenRepository.save(token);
    }
}

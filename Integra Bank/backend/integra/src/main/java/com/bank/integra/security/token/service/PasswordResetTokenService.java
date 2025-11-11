package com.bank.integra.security.token.service;

import com.bank.integra.security.token.repository.ResetPasswordTokenRepository;
import com.bank.integra.user.model.UserDetails;
import com.bank.integra.user.model.User;
import com.bank.integra.security.token.model.PasswordResetToken;
import com.bank.integra.user.service.UserService;
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

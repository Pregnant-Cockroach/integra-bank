package com.bank.integra.services.validation;


import com.bank.integra.enums.EmailValidationResponse;
import com.bank.integra.services.API.DisposableEmailChecker;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

    private static DisposableEmailChecker disposableEmailChecker;

    @Autowired
    public EmailValidator(DisposableEmailChecker disposableEmailChecker) {
        EmailValidator.disposableEmailChecker = disposableEmailChecker;
    }

    public static EmailValidationResponse checkEmail(String email, Integer userId, UserService userService) {
        if(email == null || email.trim().isEmpty()) {
            return EmailValidationResponse.INVALID_FORMAT;
        }

        email = email.trim();

        if(email.length() > 254) {
            return EmailValidationResponse.TOO_LONG;
        }

        if(!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            return EmailValidationResponse.INVALID_FORMAT;
        }

        if(disposableEmailChecker.isEmailDisposable(email)) {
            return EmailValidationResponse.DISPOSABLE_EMAIL;
        }

        if(userService.existsByUserId(userId) && userService.getUserDetailsByUserId(userId).getEmail().equals(email)) {
            return EmailValidationResponse.EMAIL_IS_SAME_AS_CURRENT;
        }

        if(userService.existsByEmail(email)) {
            return EmailValidationResponse.ALREADY_TAKEN;
        }

        return EmailValidationResponse.OK;
    }

}

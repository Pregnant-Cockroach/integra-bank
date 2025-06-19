package com.bank.integra.exceptions.handlers;

import com.bank.integra.enums.PaymentValidationResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.INVALID_FORMAT.getDescription());

        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer; // возвращаем назад на предыдущую страницу
        } else {
            return "redirect:/"; // или куда-то по умолчанию
        }
    }
}

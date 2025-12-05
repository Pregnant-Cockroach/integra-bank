package com.bank.integra.general.aop;

import com.bank.integra.general.enums.PaymentValidationResponse;
import com.bank.integra.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Aspect
@Component
public class ValidatePaymentAspect {
    private final UserService userService;

    @Autowired
    public ValidatePaymentAspect(UserService userService) {
        this.userService = userService;
    }


    @Around("@annotation(com.bank.integra.general.aop.ValidatePayment)")
    public Object validatePayment(ProceedingJoinPoint joinPoint) throws Throwable {
        // Аргументы: [0]=authentication, [1]=recipientId, [2]=amount, [3]=model, [4]=redirectAttributes
        //TODO Ну оно хрупкое. А сможешь ли юзнуть на другую штуку?
        Object[] args = joinPoint.getArgs();

        Authentication authentication = (Authentication) args[0];
        Integer senderId = Integer.parseInt(authentication.getName());
        Integer recipientId = (Integer) args[1];
        BigDecimal amount = (BigDecimal) args[2];
        RedirectAttributes redirectAttributes = (RedirectAttributes) args[4];

        // Проверка на null (аналог checkIfUserNull)
        if (userService.getUserDetailsByUserId(recipientId) == null || userService.getUserDetailsByUserId(senderId) == null) {
            redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.INVALID_FORMAT.getDescription());
            return "redirect:/user/home"; // А может лучше вернуть тру/фолс, как в PaymentService?
        }

        // Проверка на баланс (аналог checkIfUserHasEnoughMoney)
        if (userService.getUserDetailsByUserId(senderId).getBalance().compareTo(amount) < 0) {
            redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.NOT_ENOUGH_FUNDS.getDescription());
            return "redirect:/user/home";
        }

        // Проверка на перевод самому себе (аналог checkIfUserTheSameAsCurrent)
        if (senderId.equals(recipientId)) {
            redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.ID_IS_SAME_AS_CURRENT.getDescription());
            return "redirect:/user/home";
        }

        // Проверка на бан (аналог checkIfUserIsBanned)
        if (!userService.getUserById(recipientId).isActive()) {
            redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.USER_BANNED.getDescription());
            return "redirect:/user/home";
        }

        // 1. Выполняем оригинальный метод
        Object result = joinPoint.proceed();

        return result;
    }
}

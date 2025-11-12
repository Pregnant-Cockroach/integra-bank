package com.bank.integra.transaction.controller;

import com.bank.integra.general.aop.ValidatePayment;
import com.bank.integra.general.enums.PaymentValidationResponse;
import com.bank.integra.transaction.dto.TransferDTO;
import com.bank.integra.transaction.service.PaymentService;
import com.bank.integra.transaction.service.TransactionsService;
import com.bank.integra.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

//TODO Сделать отображение кнопачки скачать пдф транзакции
@RequestMapping("/user")
@Controller
public class TransferController {
    private final PaymentService paymentService;
    private final UserService userService;
    private final TransactionsService transactionsService;

    public TransferController(PaymentService paymentService, UserService userService, TransactionsService transactionsService) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.transactionsService = transactionsService;
    }

    //TODO @RequestParam Integer senderId - выстрел себе в ногу. Не делай так. Легко подменить и будет пизда. Используй authentication
    @ValidatePayment
    @PostMapping("/transfer")
    public String makeTransfer(Authentication authentication, @RequestParam Integer recipientId,
                               @RequestParam Double amount, Model model, RedirectAttributes redirectAttributes) {

        Integer senderId = Integer.parseInt(authentication.getName());
        TransferDTO transferDTO = new TransferDTO(senderId, recipientId, amount,
                    userService.getUserDetailsByUserId(senderId).getBalance(),
                    userService.getUserDetailsByUserId(recipientId).getFirstName(),
                    userService.getUserDetailsByUserId(recipientId).getLastName());

        model.addAttribute("transferData", transferDTO);
        model.addAttribute("transactionOn", "true");
        return "confirmPayment";
    }



    @PostMapping("confirm-transfer")
    public String confirmTransfer(Authentication authentication,
                                  @RequestParam Integer recipientId,
                                  @RequestParam Double amount,
                                  @RequestParam UUID idempotencyKey, RedirectAttributes redirectAttributes) {
        try {
            Integer senderId = Integer.parseInt(authentication.getName());
            paymentService.makePayment(senderId, recipientId, amount, idempotencyKey);
        } catch(RuntimeException e) {
            System.out.println("Duplicate of transaction.");
        }
        redirectAttributes.addFlashAttribute("information", "Transaction successful");
        return "redirect:/user/home";
    }
}

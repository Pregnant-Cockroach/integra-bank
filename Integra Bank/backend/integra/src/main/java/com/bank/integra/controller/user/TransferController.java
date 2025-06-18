package com.bank.integra.controller.user;

import com.bank.integra.enums.PaymentValidationResponse;
import com.bank.integra.services.DTO.TransferDTO;
import com.bank.integra.services.bank.PaymentService;
import com.bank.integra.services.bank.TransactionsService;
import com.bank.integra.services.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

//TODO Сделать отображение кнопачки скачать пдф транзакции
@RequestMapping("/user")
@Controller
public class TransferController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsService transactionsService;

    //TODO case 1, 0, 3 - дриндж, переведи в енумы с константными значениями.
    @PostMapping("/transfer")
    public String makeTransfer(@RequestParam Integer senderId,
                               @RequestParam String recipientId,
                               @RequestParam String amount, Model model, RedirectAttributes redirectAttributes) {

        if (!PaymentService.checkIfFormatCorrect(recipientId, amount)) {
            redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.INVALID_FORMAT.getDescription());
            return "redirect:/user/home";
        }
        Integer recipientIds = Integer.parseInt(recipientId);
        Double amounts = Double.parseDouble(amount);
        switch(checkBeforePayment(senderId,recipientIds,amounts)){
            case 0:
                redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.INVALID_FORMAT.getDescription());
                return "redirect:/user/home";
            case 1:
                redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.NOT_ENOUGH_FUNDS.getDescription());
                return "redirect:/user/home";
            case 2:
                redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.ID_IS_SAME_AS_CURRENT.getDescription());
                return "redirect:/user/home";
            case 3:
                redirectAttributes.addFlashAttribute("information", PaymentValidationResponse.USER_BANNED.getDescription());
                return "redirect:/user/home";

        }
        TransferDTO transferDTO = new TransferDTO(senderId, recipientIds, amounts,
                    userService.getUserDetailsByUserId(senderId).getBalance(),
                    userService.getUserDetailsByUserId(recipientIds).getFirstName(),
                    userService.getUserDetailsByUserId(recipientIds).getLastName());

        model.addAttribute("transferData", transferDTO);
        model.addAttribute("transactionOn", "true");
        return "confirmPayment";
    }

    //TODO Для проверок лучше попробовать оформить АОП в аннотацию.

    private int checkBeforePayment(Integer senderId, Integer recipientId, Double amount) {
        if(PaymentService.checkIfUserNull(senderId, recipientId, userService)) return 0;
        if(PaymentService.checkIfUserHasEnoughMoney(amount, userService.getUserDetailsByUserId(senderId))) return 1;
        if(PaymentService.checkIfUserTheSameAsCurrent(senderId, recipientId, userService)) return 2;
        if(PaymentService.checkIfUserIsBanned(recipientId, userService)) return 3;
        return 2;
    }


    @PostMapping("confirm-transfer")
    public String confirmTransfer(@RequestParam Integer senderId,
                                  @RequestParam Integer recipientId,
                                  @RequestParam Double amount,
                                  @RequestParam UUID idempotencyKey, RedirectAttributes redirectAttributes) {
        try {
            paymentService.makePayment(senderId, recipientId, amount, idempotencyKey);
        } catch(RuntimeException e) {
            System.out.println("Duplicate of transaction.");
        }
        redirectAttributes.addFlashAttribute("information", "Transaction successful");
        return "redirect:/user/home";
    }
}

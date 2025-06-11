package com.bank.integra.services.DTO;

import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.services.person.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

// TODO: Рассмотреть добавление валидации через аннотации, например:
// @NotNull, @Min(1), @DecimalMin("0.01") над полями и использование @Valid в контроллере
// Это упростит проверку входных данных и улучшит читаемость

@Data
public class TransferDTO {
    private Integer senderId;
    private Integer recipientId;
    private Double amount;
    private Double currentBalance;
    private String recipientName;
    private String recipientLastName;

    public TransferDTO(Integer senderId, Integer recipientId, Double amount, Double currentBalance, String recipientName, String recipientLastName) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.recipientName = recipientName;
        this.recipientLastName = recipientLastName;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", amount=" + amount +
                ", currentBalance=" + currentBalance +
                ", recipientName='" + recipientName + '\'' +
                ", recipientLastName='" + recipientLastName + '\'' +
                '}';
    }
}

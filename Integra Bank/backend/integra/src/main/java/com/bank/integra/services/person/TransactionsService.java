package com.bank.integra.services.person;

import com.bank.integra.dao.TransactionsRepository;
import com.bank.integra.dao.UserDetailsRepository;
import com.bank.integra.entities.details.Transaction;
import com.bank.integra.entities.details.UserDetails;
import com.bank.integra.services.customTools.OlegList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserService userService;


    public Transaction saveTransaction(Transaction transaction) {
        UserDetails sender = userDetailsRepository.findById(transaction.getSender().getUserId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        UserDetails recipient = userDetailsRepository.findById(transaction.getRecipient().getUserId())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Integer senderId, Integer recipientId, Double balance, String description) {
        Transaction transaction = new Transaction();
        transaction.setSender(userService.getUserDetailsByUserId(senderId));
        transaction.setRecipient(userService.getUserDetailsByUserId(recipientId));
        transaction.setBalance(balance);
        transaction.setEventTimeStamp(LocalDateTime.now());
        transaction.setDescription(description);
        return transaction;
    }

    public Transaction createAndSave(Integer senderId, Integer recipientId, Double balance, String description) {
        Transaction transaction = createTransaction(senderId, recipientId, balance, description);
        return saveTransaction(transaction);
    }

    public List<Transaction> getSentTransactions(Integer senderId) {
        return transactionRepository.findBySender(userService.getUserDetailsByUserId(senderId));
    }

    public List<Map<String, Object>> getFormattedTransactionsForUser(Integer userId) {
        UserDetails user = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> allTransactions = transactionRepository.findAll();
        List<Map<String, Object>> result = new OlegList();

        for (Transaction t : allTransactions) {
            if (t.getSender().equals(user) || t.getRecipient().equals(user)) {
                Map<String, Object> map = new HashMap<>();
                if (t.getSender().equals(user)) {
                    map.put("type", "SENT");
                    map.put("to", t.getRecipient().getFirstName() + " " + t.getRecipient().getLastName());
                    map.put("from", t.getSender().getFirstName() + " " + t.getSender().getLastName());
                } else {
                    map.put("type", "RECEIVED");
                    map.put("to", t.getRecipient().getFirstName() + " " + t.getRecipient().getLastName());
                    map.put("from", t.getSender().getFirstName() + " " + t.getSender().getLastName());
                }

                map.put("amount", t.getBalance());
                map.put("timestamp", t.getEventTimeStamp());
                map.put("description", t.getDescription());

                result.add(map);
            }
        }

        return result;
    }



    public List<Transaction> getReceivedTransactions(Integer recipientId) {
        return transactionRepository.findByRecipient(userService.getUserDetailsByUserId(recipientId));
    }
}


package com.bank.integra.transaction.repository;

import com.bank.integra.transaction.model.Transaction;
import com.bank.integra.user.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findBySender(UserDetails sender);
    List<Transaction> findByRecipient(UserDetails recipient);
    boolean existsByIdempotencyKey(String idempotencyKey);
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}

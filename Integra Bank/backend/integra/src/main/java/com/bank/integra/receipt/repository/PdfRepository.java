package com.bank.integra.receipt.repository;

import com.bank.integra.receipt.model.PdfReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PdfRepository extends JpaRepository<PdfReceipt, Integer> {
    Optional<PdfReceipt> findByTransactionId(String id);

}

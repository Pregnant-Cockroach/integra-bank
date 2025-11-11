package com.bank.integra.async;

import com.bank.integra.async.task.AsyncTask;
import com.bank.integra.async.task.EmailSendTask;
import com.bank.integra.async.task.PdfGenerationTask;
import com.bank.integra.async.service.EmailSenderService;
import com.bank.integra.receipt.service.PdfGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncManager {
    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private PdfGenerationService generationService;


    @Async("asyncTaskExecutor")
    private void runAsync(AsyncTask task) {
        try {
            task.execute();
            log.info("✅ Async task {"+ task.info() + "} выполнен");
        } catch (Exception e) {
            log.error("❌ Ошибка при выполнении async task {"+ task.info() +"}: {}", e.getMessage(), e);
        }
    }

    public void runEmailSendTask(String email) {
        AsyncTask task = new EmailSendTask(email,senderService);
        runAsync(task);
    }

    public void runPdfGenerationTask(String transactionId) {
        AsyncTask task = new PdfGenerationTask(transactionId,generationService);
        runAsync(task);
    }



}

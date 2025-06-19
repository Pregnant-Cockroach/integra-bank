package com.bank.integra.services.bank.async.manager;

import com.bank.integra.services.bank.async.enums.AsyncTaskType;
import com.bank.integra.services.bank.async.services.EmailSenderService;
import com.bank.integra.services.bank.async.services.PdfGenerationService;
import com.bank.integra.services.bank.async.tasks.AsyncTask;
import com.bank.integra.services.bank.async.tasks.EmailSendTask;
import com.bank.integra.services.bank.async.tasks.PdfGenerationTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

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

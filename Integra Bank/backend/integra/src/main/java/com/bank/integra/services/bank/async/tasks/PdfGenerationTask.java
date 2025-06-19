package com.bank.integra.services.bank.async.tasks;

import com.bank.integra.services.bank.async.enums.AsyncTaskType;
import com.bank.integra.services.bank.async.services.PdfGenerationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PdfGenerationTask implements AsyncTask{

    private final String transactionId;
    private final PdfGenerationService pdfGenerationService;

    @Override
    public void execute() {
        pdfGenerationService.generateReceiptAsync(transactionId);
    }

    @Override
    public String info() {
        return AsyncTaskType.PDF_GENERATION.name();
    }
}

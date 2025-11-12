package com.bank.integra.async.task;

import com.bank.integra.async.enums.AsyncTaskType;
import com.bank.integra.receipt.service.PdfGenerationService;
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

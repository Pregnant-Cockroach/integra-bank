package com.bank.integra.async.task;

import com.bank.integra.async.enums.AsyncTaskType;
import com.bank.integra.async.service.EmailSenderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailSendTask implements AsyncTask {

    private final String toEmail;
    private final EmailSenderService senderService;

    @Override
    public void execute() {
        senderService.sendPasswordResetConfirmation(toEmail);
    }

    @Override
    public String info() {
        return AsyncTaskType.PASSWORD_RESET_EMAIL.name();
    }
}

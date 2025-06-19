package com.bank.integra.services.bank.async.tasks;

import com.bank.integra.services.bank.async.enums.AsyncTaskType;
import com.bank.integra.services.bank.async.services.EmailSenderService;
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

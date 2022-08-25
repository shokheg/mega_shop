package com.amr.project.service.abstracts;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}

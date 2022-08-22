package com.amr.project.service.abstracts;

public interface EmailSenderService {
    void senEmail(String to, String subject, String message);
}

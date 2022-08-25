package com.amr.project.webapp.controller;

import com.amr.project.model.Mail;
import com.amr.project.service.abstracts.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendMail(@RequestBody Mail mail) {
        this.emailSenderService.sendEmail(
                mail.getTo(),
                mail.getSubject(),
                mail.getText());

        return ResponseEntity.ok("Success");
    }
}

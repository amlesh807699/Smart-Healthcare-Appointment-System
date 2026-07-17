package com.project.project.Email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Resend resend;

    @Value("${app.mail.from}")
    private String from;

    public void sendEmail(String to,
                          String subject,
                          String html) {

        CreateEmailOptions options =
                CreateEmailOptions.builder()
                        .from(from)
                        .to(to)
                        .subject(subject)
                        .html(html)
                        .build();

        try {
            resend.emails().send(options);
        } catch (ResendException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
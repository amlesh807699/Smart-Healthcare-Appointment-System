package com.project.project.Email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Resend resend;

    @Value("${app.mail.from}")
    private String from;

    @Async
    public void sendEmail(String to,
                          String subject,
                          String html) {

        CreateEmailOptions options = CreateEmailOptions.builder()
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

    @Async
    public void sendVerificationEmail(String to,
                                      String name,
                                      String verificationLink) {

        String subject = "Verify Your Email";

        String html = """
                <html>
                <body style="font-family:Arial,sans-serif">

                    <h2>Hello %s 👋</h2>

                    <p>
                        Thank you for registering.
                    </p>

                    <p>
                        Click the button below to verify your email.
                    </p>

                    <a href="%s"
                       style="
                           background:#2563eb;
                           color:white;
                           padding:12px 20px;
                           text-decoration:none;
                           border-radius:8px;
                           display:inline-block;">
                        Verify Email
                    </a>

                    <p>
                        This link will expire in <b>15 minutes</b>.
                    </p>

                </body>
                </html>
                """.formatted(name, verificationLink);

        sendEmail(to, subject, html);
    }
}
package com.Myprojects.Dynamic_Form_Builder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.recipient}")
    private String notificationEmailRecipient;

    @Value("${spring.mail.username}")
    private String notificationEmailSender;

    public void sendFormSubmissionNotification(String subject, String errorMessage, String requestBody) {
        try {
            String emailContent = createEmailContent(errorMessage, requestBody);
            sendEmail(notificationEmailRecipient, subject, emailContent);
        } catch (Exception e) {
            logger.error("Failed to send email notification: {}", e.getMessage(), e);
        }
    }

    private String createEmailContent(String errorMessage, String requestBody) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Hi Team,<br><br>");
        emailContent.append("An issue occurred during a form submission.<br><br>");
        emailContent.append("Error Message: ").append(errorMessage).append("<br><br>");
        emailContent.append("Request Body: ").append(requestBody).append("<br><br>");
        emailContent.append("Please review the details.<br><br>");
        emailContent.append("Cheers,<br>Dynamic Form Builder Team.");
        return emailContent.toString();
    }

    private void sendEmail(String recipient, String subject, String emailContent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(notificationEmailSender, "Dynamic Form Builder Team");
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendUnauthorizedNotification(String details) {
        sendFormSubmissionNotification("Unauthorized Access Attempt", "Unauthorized access attempt detected. Details: " + details, "");
    }

    public void sendErrorNotification(String errorMessage, String requestBody) {
        sendFormSubmissionNotification("Error Notification", errorMessage, requestBody);
    }

    public void sendExceptionNotification(String exceptionMessage, String requestBody) {
        sendFormSubmissionNotification("Exception Notification", exceptionMessage, requestBody);
    }

    public void sendValidationErrorNotification(String errorMessage, String requestBody) {
        sendFormSubmissionNotification("Form Validation Error", errorMessage, requestBody);
    }
}


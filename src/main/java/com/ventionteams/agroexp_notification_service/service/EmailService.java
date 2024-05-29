package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.service.messaging.MessageReceiver;
import com.ventionteams.agroexp_notification_service.util.ImageConversionUtil;
import com.ventionteams.agroexp_notification_service.util.MessageFormingUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String ENCODING = "UTF-8";
    private static final String TEMPLATE = "emailTemplate.html";

    private final JavaMailSender javaMailSender;
    private final MessageReceiver messageReceiver;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    public void send(Message<?> payload) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String html = templateEngine.process(TEMPLATE, MessageFormingUtil.formEmailMessage());
        try {
            var helper = new MimeMessageHelper(message, true, ENCODING);
            helper.setFrom(username);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new MailSendException(String.format("Failed to send email notification for user %s", user.getEmail()), e);
        }

        javaMailSender.send(message);
        log.info(String.format("Mail has been sent to %s, subject: %s", user.getEmail(), subject));
    }
}

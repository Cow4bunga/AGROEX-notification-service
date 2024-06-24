package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.util.MessageFormingUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

  private static final String ENCODING = "UTF-8";
  private static final String TEMPLATE = "emailTemplate.html";

  private final JavaMailSender javaMailSender;
  private final SpringTemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String username;

  public void send(NotificationPayload notificationPayload) {
    MimeMessage message = javaMailSender.createMimeMessage();
    String html =
        templateEngine.process(TEMPLATE, MessageFormingUtil.formEmailMessage(notificationPayload));

    try {
      var helper = new MimeMessageHelper(message, true, ENCODING);
      helper.setFrom(username);
      helper.setTo(notificationPayload.getEmail());
      helper.setSubject(String.valueOf(notificationPayload.getEvent().getEventName()));
      helper.setText(html, true);
    } catch (MessagingException e) {
      throw new MailSendException(
          String.format(
              "Failed to send email notification for user %s", notificationPayload.getEmail()),
          e);
    }

    javaMailSender.send(message);
    log.info(
        String.format(
            "Mail has been sent to %s, subject: %s",
            notificationPayload.getEmail(), notificationPayload.getEvent().getEventName()));
  }
}

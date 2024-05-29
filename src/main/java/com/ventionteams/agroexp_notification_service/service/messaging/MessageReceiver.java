package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.service.EmailService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MessageReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
  @Autowired private SqsTemplate sqsTemplate;
  @Autowired private EmailService emailService;

  @SqsListener("Agroex-notification-queue")
  public void listen(Message<?> message) {
    LOGGER.info("Message received on listen method at {}", OffsetDateTime.now());
    emailService.send(message);
    Acknowledgement.acknowledge(message);
  }

  // this method should be called in an infinite loop
  public void receive() {
    sqsTemplate.receive(from -> from.queue("Agroex-notification-queue"));
  }
}

package com.ventionteams.agroexp_notification_service.util;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public final class MessageFormingUtil {
  public static Context formEmailMessage(NotificationPayload notificationPayload) {
    var logoLink = ImageConversionUtil.convertImageToBase64("logo.png");
    Context context = new Context();
    var event = notificationPayload.getEvent();

    context.setVariable("logoLink", logoLink);
    context.setVariable("subject", String.valueOf(event));

    switch (event){
      case LOT_ACCEPTANCE -> context.setVariable("text",String.format("sjvjv!",notificationPayload.getLotTitle()));
      case LOT_REJECTION -> context.setVariable("text",String.format("Your lot %s has been rejected!",notificationPayload.getLotTitle()));
      case LOT_PURCHASED -> context.setVariable("text",String.format("Lot %s has been purchased!",notificationPayload.getLotTitle()));
      case BET_OUTBID -> context.setVariable("text",String.format("Your bet on lot %s has been outbid!",notificationPayload.getLotTitle()));
      case LOT_EXPIRED -> context.setVariable("text",String.format("Your lot %s has expired!",notificationPayload.getLotTitle()));
      case null, default -> throw new MailSendException(String.format("Could nor send notification on lot %s!", notificationPayload.getLotTitle()));
    }

    return context;
  }
}

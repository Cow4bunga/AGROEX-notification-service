package com.ventionteams.agroexp_notification_service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public final class MessageFormingUtil {
    public static Context formEmailMessage(){
        var logoLink = ImageConversionUtil.convertImageToBase64("logo.png");
        Context context = new Context();
        context.setVariable("logoLink", logoLink);

        return context;
    }
}

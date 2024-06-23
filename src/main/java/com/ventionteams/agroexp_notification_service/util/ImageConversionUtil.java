package com.ventionteams.agroexp_notification_service.util;

import java.io.IOException;

import com.ventionteams.agroexp_notification_service.exception.ImageConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Slf4j
@Service
public final class ImageConversionUtil {

  public static String convertImageToBase64(String imageClassPath) {
    try {
      var resource = new ClassPathResource(imageClassPath);
      var imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
      return java.util.Base64.getEncoder().encodeToString(imageBytes);
    } catch (IOException e) {
      throw new ImageConversionException("Error while image conversion", e);
    }
  }
}

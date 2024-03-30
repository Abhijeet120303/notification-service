package org.dnyanyog.controller;

import java.time.LocalDateTime;
import junit.framework.Assert;
import org.dnyanyog.dto.request.NotificationRequest;
import org.dnyanyog.dto.response.NotificationResponse;
import org.dnyanyog.entity.Notification;
import org.dnyanyog.enums.NotificationResponseCode;
import org.dnyanyog.repository.NotificationRepository;
import org.dnyanyog.service.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class NotificationServiceUnitTestingMockito {

  @Mock NotificationRepository repo;

  @InjectMocks NotificationServiceImpl notificationService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void verifyNotificationSaveOperationReturnExpectedObject() {
    NotificationRequest request = new NotificationRequest();
    request.setClientId("CLIENT001");
    request.setMode("EMAIL");
    request.setSubject("Important Notification");
    request.setBody("Hello, this is an important notification.");
    request.setFooter("Best regards, Your App");
    request.setFrom("jspm120303@gmail.com");
    request.setTo("nirphalabhijeet1@gmail.com");

    Notification notificationEntity = new Notification();
    notificationEntity
        .setClient_id("CLIENT001")
        .setBody("Hello, this is an important notification.")
        .setMode("EMAIL")
        .setSubject("Important Notification")
        .setFooter("Best regards, Your App")
        .setFrom_email("jspm120303@gmail.com")
        .setTo_email("nirphalabhijeet1@gmail.com")
        .setCreated_date(LocalDateTime.now())
        .setUpdated_date(LocalDateTime.now());

    Mockito.when(repo.save(Mockito.any())).thenReturn(notificationEntity);

    NotificationResponse response = notificationService.sendEmail(request);

    Assert.assertEquals(
        NotificationResponseCode.SUCCESS_NOTIFICATION_SENT.getStatus(), response.getStatus());
    Assert.assertEquals(
        NotificationResponseCode.SUCCESS_NOTIFICATION_SENT.getCode(), response.getCode());
    Assert.assertEquals(
        NotificationResponseCode.SUCCESS_NOTIFICATION_SENT.getMessage(), response.getMessage());
    Assert.assertNotNull(response.getTimeStamp());
  }

  @Test
  public void verifyNotificationOperationReturnErrorMessageOnException() {

    NotificationRequest request = new NotificationRequest();
    request.setClientId("CLIENT001");
    request.setMode("EMAIL");
    request.setSubject("Important Notification");
    request.setBody("Hello, this is an important notification.");
    request.setFrom("jspm120303@gmail.com");
    request.setTo("nirphalabhijeet1@gmail.com");

    Notification notificationEntity = new Notification();
    notificationEntity
        .setClient_id("CLIENT001")
        .setBody("Hello, this is an important notification.")
        .setMode("EMAIL")
        .setSubject("Important Notification")
        .setFrom_email("jspm120303@gmail.com")
        .setTo_email("nirphalabhijeet1@gmail.com")
        .setCreated_date(LocalDateTime.now())
        .setUpdated_date(LocalDateTime.now());

    Mockito.when(repo.save(Mockito.any())).thenReturn(notificationEntity);

    NotificationResponse response = notificationService.sendEmail(request);

    Assert.assertEquals(
        NotificationResponseCode.ERROR_CATCH_BLOCK.getStatus(), response.getStatus());
    Assert.assertEquals(NotificationResponseCode.ERROR_CATCH_BLOCK.getCode(), response.getCode());
    Assert.assertEquals(
        NotificationResponseCode.ERROR_CATCH_BLOCK.getMessage(), response.getMessage());
    Assert.assertNotNull(response.getTimeStamp());
  }
}

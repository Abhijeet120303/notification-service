package org.dnyanyog.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.dnyanyog.NotificationMain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = NotificationMain.class)
public class NotificationIntegrationTestXmlController {

  @Autowired MockMvc mockMvc;

  @Test
  public void verifyNotificationOperationForNotificationSuccess() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/notification/v1/notify")
            .content(
                "<NotificationRequest>\r\n"
                    + "<clientId>CLIENT001</clientId>\r\n"
                    + "<mode>EMAIL</mode>\r\n"
                    + "<subject>Important Notification</subject>\r\n"
                    + "<body>Hello, this is an important notification.</body>\r\n"
                    + "<footer>Best regards, Your App</footer>\r\n"
                    + "<from>jspm120303@gmail.com</from>\r\n"
                    + "<to>nirphalabhijeet1@gmail.com</to>\r\n"
                    + "</NotificationRequest>")
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE);

    mockMvc
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/status").string("Success"))
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/code").string("0000"))
        .andExpect(
            MockMvcResultMatchers.xpath("/NotificationResponse/message")
                .string("Notification sent successfully!"))
        .andReturn();
  }

  @Test
  public void verifyNotificationOperationForIncompleteDataProvided() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/notification/v1/notify")
            .content(
                "<NotificationRequest>\r\n"
                    + "<clientId>CLIENT001</clientId>\r\n"
                    + "<mode>EMAIL</mode>\r\n"
                    + "<body>Hello, this is an important notification.</body>\r\n"
                    + "<footer>Best regards, Your App</footer>\r\n"
                    + "<from>sender@example.com</from>\r\n"
                    + "<to>recipient@example.com</to>\r\n"
                    + "</NotificationRequest>")
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE);

    mockMvc
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/status").string("Fail"))
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/code").string("NOTI0001"))
        .andExpect(
            MockMvcResultMatchers.xpath("/NotificationResponse/message")
                .string("Incomplete data sent"))
        .andReturn();
  }

  @Test
  public void verifyNotificationOperationForInvalidMode() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/notification/v1/notify")
            .content(
                "<NotificationRequest>\r\n"
                    + "<clientId>CLIENT001</clientId>\r\n"
                    + "<mode>MAIL</mode>\r\n"
                    + "<subject>Important Notification</subject>\r\n"
                    + "<body>Hello, this is an important notification.</body>\r\n"
                    + "<footer>Best regards, Your App</footer>\r\n"
                    + "<from>sender@example.com</from>\r\n"
                    + "<to>recipient@example.com</to>\r\n"
                    + "</NotificationRequest>")
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE);

    mockMvc
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/status").string("Fail"))
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/code").string("NOTI0002"))
        .andExpect(
            MockMvcResultMatchers.xpath("/NotificationResponse/message")
                .string("Invalid notification mode"))
        .andReturn();
  }

  @Test
  public void verifyNotificationOperationForToEmailIfModeIsMail() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/notification/v1/notify")
            .content(
                "<NotificationRequest>\r\n"
                    + "<clientId>CLIENT001</clientId>\r\n"
                    + "<mode>EMAIL</mode>\r\n"
                    + "<subject>Important Notification</subject>\r\n"
                    + "<body>Hello, this is an important notification.</body>\r\n"
                    + "<footer>Best regards, Your App</footer>\r\n"
                    + "<from>sender@example.com</from>\r\n"
                    + "<to>recipientexample.com</to>\r\n"
                    + "</NotificationRequest>")
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE);

    mockMvc
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/status").string("Fail"))
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/code").string("NOTI0003"))
        .andExpect(
            MockMvcResultMatchers.xpath("/NotificationResponse/message")
                .string("Invalid email address for To EMAIL"))
        .andReturn();
  }

  @Test
  public void verifyNotificationOperationForCatchBlockResponse() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/notification/v1/notify")
            .content(
                "<NotificationRequest>\r\n"
                    + "<clientId>CLIENT001</clientId>\r\n"
                    + "<mode>EMAIL</mode>\r\n"
                    + "<subject>Important Notification</subject>\r\n"
                    + "<body>Hello, this is an important notification.</body>\r\n"
                    + "<from>sender@example.com</from>\r\n"
                    + "<to>recipient@example.com</to>\r\n"
                    + "</NotificationRequest>")
            .contentType(MediaType.APPLICATION_XML_VALUE)
            .accept(MediaType.APPLICATION_XML_VALUE);

    mockMvc
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/status").string("Fail"))
        .andExpect(MockMvcResultMatchers.xpath("/NotificationResponse/code").string("NOTI0004"))
        .andExpect(
            MockMvcResultMatchers.xpath("/NotificationResponse/message")
                .string("Error occurred while saving or sending notification"))
        .andReturn();
  }
}

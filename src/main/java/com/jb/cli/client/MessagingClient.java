package com.jb.cli.client;

import com.jb.cli.model.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Service client for Messaging (SMS) related API operations
 */
@Component
public class MessagingClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.sms.send.url}")
    private String url;

    /**
     * Builds a message wrapper object to send a text SMS message
     *
     * @param toNumber   the to phone number with country code
     * @param fromNumber the from phone number with country code
     * @param text       the text to send
     * @return the message object to send
     */
    private MessageHolder buildSmsTextMessage(String toNumber, String fromNumber, String text) {
        MessageHolder messageHolder = new MessageHolder();
        PhoneNumber to = new PhoneNumber();
        to.setNumber(toNumber);
        to.setType("sms");
        PhoneNumber from = new PhoneNumber();
        from.setNumber(fromNumber);
        from.setType("sms");

        MessageContent messageContent = new MessageContent();
        messageContent.setText(text);
        messageContent.setType("text");

        Message message = new Message();
        message.setContent(messageContent);

        messageHolder.setFrom(from);
        messageHolder.setTo(to);
        messageHolder.setMessage(message);

        return messageHolder;
    }

    /**
     * Sends SMS text message using the specified JWT token
     *
     * @param jwt        a JSON Web token
     * @param toNumber   the to phone number with country code
     * @param fromNumber the from phone number with country code
     * @param text       the text to send
     * @return a message guid in case of success
     */
    public SendMessageResponse sendSms(String jwt, String toNumber, String fromNumber, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + jwt);

        MessageHolder messageHolder = buildSmsTextMessage(toNumber, fromNumber, text);

        HttpEntity<MessageHolder> requestEntity =
                new HttpEntity<>(messageHolder, headers);
        HttpEntity<SendMessageResponse> response =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                        SendMessageResponse.class);
        return response.getBody();
    }

}

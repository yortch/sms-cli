package com.jb.cli.model.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Response from send message operation
 */
public class SendMessageResponse implements Serializable {

    @JsonProperty("message_uuid")
    private String messageUuid;


    public String getMessageUuid() {
        return messageUuid;
    }

    public void setMessageUuid(String messageUuid) {
        this.messageUuid = messageUuid;
    }
}

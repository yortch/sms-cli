package com.jb.cli.model.messaging;

import java.io.Serializable;

/**
 * Intermediate object used by MessageHolder and includes MessageContent instance to match Messaging API
 */
public class Message implements Serializable {

    private MessageContent content;


    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }
}

package com.jb.cli.model.messaging;

import java.io.Serializable;

/**
 * Main message holder object used to send in Messaging API
 */
public class MessageHolder implements Serializable {

    private PhoneNumber to;

    private PhoneNumber from;

    private Message message;

    public PhoneNumber getTo() {
        return to;
    }

    public void setTo(PhoneNumber to) {
        this.to = to;
    }

    public PhoneNumber getFrom() {
        return from;
    }

    public void setFrom(PhoneNumber from) {
        this.from = from;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

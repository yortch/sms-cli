package com.jb.cli.model.messaging;

import java.io.Serializable;

/**
 * Message content includes the type and text of the message
 */
public class MessageContent implements Serializable {

    private String type;

    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

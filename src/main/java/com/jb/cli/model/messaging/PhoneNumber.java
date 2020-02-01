package com.jb.cli.model.messaging;

import java.io.Serializable;

/**
 * Used to hold to/from fields
 */
public class PhoneNumber implements Serializable {

    private String type;

    private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

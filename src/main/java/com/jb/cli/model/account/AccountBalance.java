package com.jb.cli.model.account;

import java.io.Serializable;

/**
 * Serializable object that matches Account API
 */
public class AccountBalance implements Serializable {

    private double value;

    private boolean autoReload;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }
}

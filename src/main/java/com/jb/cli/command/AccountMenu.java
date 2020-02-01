package com.jb.cli.command;

import com.jb.cli.client.AccountClient;
import com.jb.cli.model.account.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Command shell with Account related CLI commands
 */
@ShellComponent
public class AccountMenu {

    @Autowired
    private AccountClient accountClient;

    @ShellMethod("Gets account balance")
    public double balance(String apiKey, String apiSecret) {
        AccountBalance accountBalance = accountClient.getAccountBalance(apiKey, apiSecret);
        return accountBalance.getValue();
    }

}
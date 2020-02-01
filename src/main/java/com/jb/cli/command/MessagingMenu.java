package com.jb.cli.command;

import com.jb.cli.client.MessagingClient;
import com.jb.cli.model.messaging.SendMessageResponse;
import com.jb.cli.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Command shell with Messaging related CLI commands
 */
@ShellComponent
public class MessagingMenu {

    @Autowired
    private MessagingClient messagingClient;

    @Autowired
    private JwtHelper jwtHelper;

    private String privateKeyPath;

    private String applicationId;

    @ShellMethod("Sends SMS text message. Phone numbers must include country code.\n" +
            "\tUsage: sms --to <number> --from <number> --text <message> \n")
    public String sms(String to, String from, String text) {
        if (this.applicationId == null) {
            throw new IllegalArgumentException("applicationId has not been provided, run setup to provide it");
        }
        String jwt = jwtHelper.createJWT(this.privateKeyPath, this.applicationId);
        SendMessageResponse response = messagingClient.sendSms(jwt, to, from, text);
        return response.getMessageUuid();
    }

    @ShellMethod("Setup required configuration to send sms message" +
            "\tUsage: setup --application-id <guid> \n" +
            "\t[Optional] --key <path to private key> (default: ~/private.key)")
    public void setup(String applicationId, @ShellOption(defaultValue = "~/private.key") String key) {
        this.privateKeyPath = key;
        this.applicationId = applicationId;
    }

}

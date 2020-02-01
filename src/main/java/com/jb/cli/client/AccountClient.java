package com.jb.cli.client;

import com.jb.cli.model.account.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service client for Account related API operations
 */
@Component
public class AccountClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.balance.get.url}")
    private String url;

    /***
     * Gets user's account balance based on api key and secret
     * @param apiKey user's API key
     * @param apiSecret user's API secret
     * @return returns account balance
     */
    public AccountBalance getAccountBalance(String apiKey, String apiSecret) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey)
                .queryParam("api_secret", apiSecret);
        return restTemplate.getForObject(uriBuilder.toUriString(), AccountBalance.class);
    }

}

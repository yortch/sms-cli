package com.jb.cli.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.cli.config.ApplicationConfiguration;
import com.jb.cli.model.account.AccountBalance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes = {ApplicationConfiguration.class, AccountClient.class})
public class AccountClientTest {

    @Autowired
    private AccountClient accountClient;

    @Value("${application.balance.get.url}")
    private String url;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;


    @PostConstruct
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        final ClientHttpRequestFactory requestFactory =
                (ClientHttpRequestFactory) ReflectionTestUtils.getField(restTemplate, "requestFactory");

        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));
    }

    /**
     * Uncomment to test with real API values
     * TODO: change to use Spring profiles so that it can be run as "integration" test
     */
    //@Test
    public void getAccountBalanceTest() {
        assertThat(accountClient).isNotNull();
        AccountBalance accountBalance = accountClient.getAccountBalance(apiKey, apiSecret);
        assertThat(accountBalance).isNotNull();
        assertThat(accountBalance.getValue()).isGreaterThan(0);
    }

    @Test
    public void getAccountBalanceMockTest() {
        assertThat(accountClient).isNotNull();

        AccountBalance expectedBalance = new AccountBalance();
        expectedBalance.setAutoReload(true);
        expectedBalance.setValue(222.22);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(matchesPattern(url + ".*")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(expectedBalance))
                    );
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage(), e);
        }

        AccountBalance actualBalance = accountClient.getAccountBalance(apiKey, apiSecret);
        assertThat(actualBalance).isNotNull();
        assertThat(actualBalance.getValue()).isEqualTo(expectedBalance.getValue());
        assertThat(actualBalance.isAutoReload()).isEqualTo(expectedBalance.isAutoReload());
    }

}

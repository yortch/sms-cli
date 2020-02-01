package com.jb.cli.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.cli.config.ApplicationConfiguration;
import com.jb.cli.model.messaging.SendMessageResponse;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes = {ApplicationConfiguration.class, MessagingClient.class})
public class MessagingClientTest {

    @Autowired
    private MessagingClient messagingClient;

    @Value("${application.sms.send.url}")
    private String url;

    @Value("${jwt}")
    private String jwt;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;


    @PostConstruct
    private void setUp() {
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
    public void testSendSmsTest() {
        String from = "14121234567";
        String to = "14121234567";
        assertThat(messagingClient).isNotNull();

        SendMessageResponse messageResponse = messagingClient.sendSms(jwt, to, from, "This is a test message");
        assertThat(messageResponse).isNotNull();
        assertThat(messageResponse.getMessageUuid()).isNotBlank();
    }


    @Test
    public void getAccountBalanceMockTest() {
        String from = "14121234567";
        String to = "14121234567";
        assertThat(messagingClient).isNotNull();

        SendMessageResponse response = new SendMessageResponse();
        response.setMessageUuid(UUID.randomUUID().toString());

        ObjectMapper mapper = new ObjectMapper();

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(matchesPattern(url + ".*")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.ACCEPTED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(response))
                    );
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage(), e);
        }

        SendMessageResponse messageResponse = messagingClient.sendSms(jwt, to, from, "This is a test message");
        assertThat(messageResponse).isNotNull();
        assertThat(messageResponse.getMessageUuid()).isNotBlank();
    }

}

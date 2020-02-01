package com.jb.cli.config;

import com.jb.cli.util.RequestResponseLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Sets up application configuration beans
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * REST Template use to invoke API
     *
     * @return returns initialized RESTTemplate
     */
    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //TODO: setup connection timeouts
        //BufferingClient is required so that response is available after logging it
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        //Interceptor used to log request and response
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

        return restTemplate;
    }

}

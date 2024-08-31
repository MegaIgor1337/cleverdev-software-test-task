package com.lam.migrationservice.configuration;

import com.lam.migrationservice.scheduler.configuration.MigrationProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfiguration {

    @Autowired
    private MigrationProps migrationProps;
    @Bean
    public RestTemplate restTemplateClient(RestTemplateBuilder builder) {
        String baseUrl = "https://" + migrationProps.getClient().getServer() + ":" + migrationProps.getClient().getPort();
        return builder
                .rootUri(baseUrl)
                .build();
    }

    @Bean
    public RestTemplate restTemplateNote(RestTemplateBuilder builder) {
        String baseUrl = "https://" + migrationProps.getNote().getServer() + ":" + migrationProps.getNote().getPort();
        return builder
                .rootUri(baseUrl)
                .build();
    }
}

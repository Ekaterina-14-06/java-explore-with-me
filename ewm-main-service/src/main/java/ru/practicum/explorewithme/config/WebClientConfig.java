package ru.practicum.explorewithme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ewm-stats-server.url}")
    private String serviceUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(serviceUrl)
                .build();
    }
}

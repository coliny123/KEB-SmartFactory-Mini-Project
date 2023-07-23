package com.example.javaclientserver.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
//@EnableJpaRepositories(basePackages = "com.example.javaclientserver.server.model.repository")
public class SensorDataConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}


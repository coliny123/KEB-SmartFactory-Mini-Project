package com.example.javaclientserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// Other imports if needed


@Service
public class SensorDataService {

    private final WebClient webClient;

    private static final String DATA_URL = "http://172.30.1.26/data:80";

    public SensorDataService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getDataFromOtherServer() {
        return webClient.get()
                .uri("DATA_URL")
                .retrieve()
                .bodyToMono(String.class);
    }
}
package com.example.javaclientserver.server.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ESP32Client {
    private final WebClient webClient;

    private static final String URL = "http://172.30.1.32:80"; // ESP32 server URL, IP 주소와 포트 번호 변경
    private static final String URL_DATA = URL + "/data";

    private boolean isFirstRequest = true;

    @Autowired
    public ESP32Client(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> fetchEsp32Data() {
        String requestUrl = isFirstRequest ? URL : URL_DATA; // 첫 요청 여부에 따라 URL을 선택
        isFirstRequest = false;
        WebClient.ResponseSpec responseSpec = webClient
                .get()
                .uri(requestUrl)
                .retrieve();
        Mono<String> responseBody = responseSpec.bodyToMono(String.class);
        responseBody.subscribe(response -> System.out.println("Fetched data: " + response));

        return responseBody;
    }
}
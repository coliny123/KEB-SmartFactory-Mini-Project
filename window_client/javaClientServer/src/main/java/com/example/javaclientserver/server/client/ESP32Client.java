package com.example.javaclientserver.server.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.javaclientserver.server.model.entity.ESP32Data;
import com.example.javaclientserver.server.model.repository.ESP32DataRepository;

@Component
@EnableScheduling
public class ESP32Client {

    private final WebClient webClient;

    private final ESP32DataRepository esp32DataRepository;

    private static final String URL = "http://172.30.1.32:80"; // ESP32 server URL, IP 주소와 포트 번호 변경
    private static final String URL_DATA = URL + "/data";

    private boolean isFirstRequest = true;

    @Autowired
    public ESP32Client(WebClient webClient, ESP32DataRepository esp32DataRepository) {
        this.webClient = webClient;
        this.esp32DataRepository = esp32DataRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void fetchEsp32Data() {
        String requestUrl = isFirstRequest ? URL : URL_DATA; // 첫 요청 여부에 따라 URL을 선택
        isFirstRequest = false;
        WebClient.ResponseSpec responseSpec = webClient
                .get()
                .uri(requestUrl)
                .retrieve();

        Mono<ESP32Data> esp32DataResponse = responseSpec.bodyToMono(ESP32Data.class);
        esp32DataResponse.subscribe(
                esp32Data -> {
                    System.out.println("Received Data: " + esp32Data);
                    esp32DataRepository.save(esp32Data);
                },
                error -> {
                    System.err.println("Error while fetching data: " + error.getMessage());
                }
        );
    }
}
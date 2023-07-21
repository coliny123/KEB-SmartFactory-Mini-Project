package com.example.javaclientserver.scheduler;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@EnableScheduling
public class SensorDataScheduler {

    private final WebClient webClient;

    private static final String URL = "http://172.30.1.26:80"; // ESP32 server URL, IP 주소와 포트 번호 변경
    private static final String URL_DATA = "http://172.30.1.26/data:80";

    private boolean isFirstRequest = true;

    @Autowired
    public SensorDataScheduler(WebClient webClient) {
        this.webClient = webClient;
    }

    @Scheduled(fixedDelay = 10000)
    public void fetchEsp32Data() {
        String requestUrl = isFirstRequest ? URL : URL_DATA; // 첫 요청 여부에 따라 URL을 선택
        isFirstRequest = false;
        WebClient.ResponseSpec responseSpec = webClient
                .get()
                .uri(requestUrl)
                .retrieve();

        Mono<String> responseBody = responseSpec.bodyToMono(String.class);
        responseBody.subscribe(
                result -> {
                    System.out.println("Received Data: " + result);
                },
                error -> {
                    System.err.println("Error while fetching data: " + error.getMessage());
                }
        );
    }
}
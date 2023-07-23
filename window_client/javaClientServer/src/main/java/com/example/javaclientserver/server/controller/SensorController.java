package com.example.javaclientserver.server.controller;

import com.example.javaclientserver.server.client.ESP32Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    private final ESP32Client esp32Client;

    public SensorController(ESP32Client esp32Client) {
        this.esp32Client = esp32Client;
    }

    @GetMapping
    public Mono<String> getSensorData() {
        return esp32Client.fetchEsp32Data();
    }
}
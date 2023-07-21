package com.example.javaclientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaClientServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaClientServerApplication.class, args);
    }
}
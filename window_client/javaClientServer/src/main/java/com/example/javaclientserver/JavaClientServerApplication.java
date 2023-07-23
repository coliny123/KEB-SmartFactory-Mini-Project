package com.example.javaclientserver;

import com.example.javaclientserver.server.model.repository.ESP32DataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.example.javaclientserver.server.model.repository"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.example.javaclientserver.server.model.entity"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
public class JavaClientServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaClientServerApplication.class, args);
    }
}
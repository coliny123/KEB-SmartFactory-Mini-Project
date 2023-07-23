//package com.example.javaclientserver.server.model.entity;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.sql.Timestamp;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class ESP32Data implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;    // timestamp를 PK로 하는 경우 검색 성능이 떨어지므로 덜 직관적이지만 훨씬 성능이 좋은 자동 증가 값을 PK로 설정
//    private Timestamp timestamp;
//    private double temperature;
//    private int brightness;
//    private int count;
//
//    // 매개변수 있는 생성자
//    @JsonCreator
//    public ESP32Data(
//                     @JsonProperty("timestamp") Timestamp timestamp,
//                     @JsonProperty("temperature") double temperature,
//                     @JsonProperty("brightness") int brightness,
//                     @JsonProperty("count") int count) {
//        // JSON 응답에서 가져온 문자열 타입의 timestamp 값을 LocalDateTime으로 변환
//        this.timestamp = timestamp;
//        this.temperature = temperature;
//        this.brightness = brightness;
//        this.count = count;
//    }
//}
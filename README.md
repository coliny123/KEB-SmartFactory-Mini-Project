## Smartfactory-Mini-Project(window App_ver)
---

## 프로젝트 개요
 본 프로젝트는 공장의 모니터링 시스템을 개발하는 것을 목표로 한다. 아두이노를 활용하여 센서 데이터를 수집하고, window 애플리케이션을 사용하여 실시간 모니터링 및 날씨 데이터 웹 크롤링 기술을 구현한다.
---

## 개발 기간
- 23.07.12 - 23.07.21
---
## 개발 환경

### Sensor & Server

- ![Arduino](https://img.shields.io/badge/Arduino-00979D?logo=arduino&logoColor=white)
  
- ![C++](https://img.shields.io/badge/C%2B%2B-00599C?logo=c%2B%2B&logoColor=white)

- ![ESP32](https://img.shields.io/badge/ESP32-blue?logo=espressif&logoColor=white)

### Client

- ![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=java&logoColor=white)
  
- ![Python](https://img.shields.io/badge/Python-3776AB?logo=python&logoColor=white)

### Framwork

- ![Java AWT](https://img.shields.io/badge/Java-AWT-ED8B00?logo=java&logoColor=white)

- ![Java Swing](https://img.shields.io/badge/Java-Swing-ED8B00?logo=java&logoColor=white)

- ![Python tkinter](https://img.shields.io/badge/Python-tkinter-3776AB?logo=python&logoColor=white)

- ![Python matplotlib](https://img.shields.io/badge/Python-matplotlib-3776AB?logo=python&logoColor=white)


## 기능 소개

- 센서 데이터 수집 및 웹 크롤링을 통한 날씨 데이터 수집
- Webserver(Arduino) 구축
- Python & Java 애플리케이션을 통한 공장 모니터링
- 실시간 그래프를 통한 온도, 조도 데이터의 시각화

### Home 대시보드

- Get 요청을 보내는 Client(Python & Java)의 ip 주소를 Webserver측에서 masking하여 Home 화면에 보여줌

### Graph 대시보드

- Client(Python & Java)가 Webserver(Arduino)에게 요청한 Sensor 데이터(온도)를 matplotlib를 통한 graph를 통해 실시간으로 보여줌

### Condition 대시보드

- Client(Python & Java)가 Webserver(Arduino)에게 요청한 Sensor 데이터를 보여줌
- Client(Python & Java)가 기상청 api를 통해 얻은 데이터 중 인천 지역의 금일 최대, 최소 온도를 크롤링하여 보여줌

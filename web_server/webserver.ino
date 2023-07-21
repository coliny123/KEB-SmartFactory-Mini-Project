#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include "oled_u8g2.h" // oled display
#include <ArduinoJson.h>



//------------------------ global variables ------------------------------------------------------------------
const char* ssid = "KT_GiGA_F2EA"; // "Dohwan";// "SmartFactory";                      // 와이파이 아이디
const char* password = "ffk8ebb167"; // "dh990921";// "inha4885";             // 와이파이 비밀번호

WebServer server(80);
OLED_U8G2 oled; // create oled object

// sensor
int temp_sensor = A2;    // temperature sensor
int photoresistor_sensor = A1;    // photoresistor_sensor
int echo_pin  = D8;               // 초음파 센서 수신부
int trig_pin  = D9; 

// led
int red_led    = D2;                     
int blue_led   = D3;                       
int green_led  = D4;                      
int yellow_led = D5;

// switch
int reset_pin = D6;



// calcul_temperature_formula
int Vo;
double R1 = 10000;
double logR2, R2, T, Tc;
double c1 = 1.009249522e-03, c2 = 2.378405444e-04, c3 = 2.019202697e-07;
double Tf = 0;

// photoresistor_var
int lux;

// counting_var
int count = 0;                                  // 카운터용 변수
int pre_time = 0;                               // 이전에 물건이 지나간 시간


//--------------------------- setup --------------------------------------------------------------------------
void setup() {
  Serial.begin(115200);  // ESP32 baud rate
  oled.setup();

  // wifi connecting setting
  WiFi.mode(WIFI_STA);        // Set to Connection Mode
  WiFi.begin(ssid, password);      // Attempt to connect to WiFi
  Serial.println("");
  while (WiFi.status() != WL_CONNECTED) {       // Keep waiting until the connection
    delay(500);
    Serial.print(".");
  }  
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  // pinMode setting
  pinMode(red_led, OUTPUT);                  
  pinMode(blue_led, OUTPUT);                 
  pinMode(green_led, OUTPUT);               
  pinMode(yellow_led, OUTPUT);
  pinMode(trig_pin, OUTPUT);
  pinMode(echo_pin, INPUT);          

  server.on("/", handleRootEvent);
  server.begin();  
  Serial.println("Web server started!");
}


//---------------------------- roop --------------------------------------------------------------------------
void loop() {
  server.handleClient();	
  oled.setLine(1, "Web Server");
  oled.display();
  server.on("/data", handleDataRequest);
  delay(5000); // 5/1000 sec
}


//--------------------------- functions ----------------------------------------------------------------------
void handleRootEvent() {	// change text/plain Form to json Form 
  String clientIP = server.client().remoteIP().toString();  // client's ip addr
  int octet1, octet2, octet3, octet4;
  sscanf(clientIP.c_str(), "%d.%d.%d.%d", &octet1, &octet2, &octet3, &octet4);
  String maskedIP = String(octet1) + ".XXX.XXX." + String(octet4); // 2nd, 3rd masking

  StaticJsonDocument<128> jsonDocument;
  jsonDocument["message"] = "Welcome SmartFactory WebServer!";
  jsonDocument["ip_address"] = maskedIP;

  String jsonResponse;
  serializeJson(jsonDocument, jsonResponse);
  server.send(200, "application/json", jsonResponse);

  Serial.println(jsonResponse); // monitoring
}

double calculateTemperature(){
  Vo = analogRead(temp_sensor); // read from temperature sensing value
  R2 = R1 * (4095.0 / (float)Vo - 1.0);
  logR2 = log(R2);
  T = (1.0 / (c1 + c2*logR2 + c3*logR2*logR2*logR2));
  Tc = round((T - 273.15) * 10) / 10.0;  // celsius
  
  return Tc;
}

void handleDataRequest() {	// change printData() function to json Form
  StaticJsonDocument<200> jsonDocument;

  jsonDocument["temperature"] = calculateTemperature();
  jsonDocument["brightness"] = catchingPhotoresistor();

  String jsonResponse;
  serializeJson(jsonDocument, jsonResponse);

  server.send(200, "application/json", jsonResponse);
  Serial.println(jsonResponse);
}

int catchingPhotoresistor(){
  lux = analogRead(photoresistor_sensor);
  return lux;
}

// int counting(){
//   long duration, distance;
//   digitalWrite(trig_pin, LOW);                // 초음파 센서 거리 센싱 시작
//   delayMicroseconds(2);
//   digitalWrite(trig_pin, HIGH);
//   delayMicroseconds(10);
//   digitalWrite(trig_pin, LOW);  

//   duration = pulseIn (echo_pin, HIGH);        // 반사되어 돌아온 초음파의 시간을 저장
//   distance = ((34 * duration) / 1000) / 2;    // 측정된 값을 cm로 변환하는 공식

//   if(distance > 2 && distance < 5){            // 물체와의 거리가 2cm 초과 10cm 미만이면
//     int now_time = millis();
//     if(now_time - pre_time > 500){           // 중복 카운트를 방지하기 위해 0.5초 초과면 
//       count += 1;                         // 한번 카운트
//       pre_time = now_time;                // 이전 시각에 현재 시각 저장
//     }
//   }
//   resetCount();

//   char text1[32] = "count : ";                // text1 count 값 표시
//   char value1[32];
//   String str1 = String(count, DEC);
//   str1.toCharArray(value1, 6);
//   strcat(text1, value1);
//   oled.setLine(2, text1); 

//   return count;
// }

// void resetCount(){
//       if(digitalRead(reset_pin) == LOW)           // 리셋 버튼을 누르면
//     {
//         Serial.println("count reset");                
//         count = 0;                              // 카운트 초기화
//     }
// }
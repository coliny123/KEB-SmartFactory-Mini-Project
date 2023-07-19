#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include "oled_u8g2.h" // oled display


const char* ssid = "SmartFactory";         // wifi ID
const char* password = "inha4885";         // wifi pwd

OLED_U8G2 oled; // create oled object
WebServer server(80);      // Create a global object for use on the webserver, setting up ports


void handleRootEvent() {
  Serial.println("main page");
  // status code 200(ok), format, message
  server.send(200, "text/plain", "Welcome SmartFactory WebServer!"); // Sending from server object!!!!!
}


void setup(void) {
  Serial.begin(115200);      // esp32 baud rate
  oled.setup();
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
  Serial.println(WiFi.localIP());                // if connected, print assigned ip address

  // 매개변수 자리에 함수이름이 들어감-> 매개변수로 함수를 던짐 ex)파이썬 데코레이터 클로저
  // 루트가 아닌 주소로 접근하면 처리해주는 함수x므로 error
  server.on("/", handleRootEvent);    // root, event handling function

  server.begin();  
  Serial.println("Web server started!");
}


void loop(void) {
  server.handleClient();      // handle clients process
  oled.setLine(1, "SmartFactory");
  oled.setLine(2, "Web Server");
  oled.display();
  delay(5); // 5/1000
}
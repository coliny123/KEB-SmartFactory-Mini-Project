#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include "oled_u8g2.h" // oled display


const char* ssid = "SmartFactory";         // wifi ID
const char* password = "inha4885";         // wifi pwd

OLED_U8G2 oled; // create oled object
WebServer server(80);      // Create a global object for use on the webserver, setting up ports

int tempSensor = A2; // temperature sensor
// formula
int Vo;
double R1 = 10000;
double logR2, R2, T, Tc;
double c1 = 1.009249522e-03, c2 = 2.378405444e-04, c3 = 2.019202697e-07;
double Tf = 0;

void handleRootEvent() {
  Serial.print("main page from ");

  String clientIP = server.client().remoteIP().toString();  // client's ip addr

  Vo = analogRead(tempSensor); // read from temperature sensing value
  R2 = R1 * (4095.0 / (float)Vo - 1.0);
  logR2 = log(R2);
  T = (1.0 / (c1 + c2*logR2 + c3*logR2*logR2*logR2));
  Tc = T - 273.15;  // celsius
  Tf = (Tc * 9.0/5.0) + 32.0;  // fahrenheit

  String message = "Welcome SmartFactory WebServer!\n\n";
  message += "Your IP address: " + clientIP;
  message = message + "\nTemperature: " + String(Tc) + "C " + "(" + String(Tf) + "F)";
  server.send(200, "text/plain", message);  // status code 200(OK), format, message
  
  Serial.println(clientIP);
  Serial.print(Tc);
  Serial.print("C (");
  Serial.print(Tf);
  Serial.println("F)");
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
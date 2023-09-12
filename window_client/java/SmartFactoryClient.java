package Observer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;  // Java 11
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class SmartFactoryClient {
    public static void main(String[] args) {
        String urlStr = "http://192.168.45.56:80/";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String[] lines = response.body().split("\n");

                SensorData sensorData = new SensorData();

                float brightness = 0;
                float temperature = 0;
                int counting = 0;

                if (lines.length >= 4) {
                    String temperatureLine = lines[2];
                    temperature = Float.parseFloat(temperatureLine.split(": ")[1]);
                    String brightnessLine = lines[3];
                    brightness = Float.parseFloat(brightnessLine.split(": ")[1]);
                    String countingLine = lines[4];
                    counting = Integer.parseInt(countingLine.split(": ")[1]);

                    CurrentConditionsDisplay conditionsDisplay = new CurrentConditionsDisplay(sensorData);
                    StatisticsDisplay statisticsDisplay = new StatisticsDisplay(sensorData);
                    GraphDisplay graphDisplay = new GraphDisplay(sensorData);

                    sensorData.setMeasurements(temperature, brightness, counting);

                } else {
                    System.out.println("Response format error!");
                }
            } else {
                System.out.println("Http connection failed : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

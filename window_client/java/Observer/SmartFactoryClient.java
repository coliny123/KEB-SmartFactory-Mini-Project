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
                if (lines.length >= 4) {
                    String ipLine = lines[2];
                    //                    String temperatureLine = lines[3];
                    String ip_address = ipLine.split(": ")[1];
                    //                    String temperature = temperatureLine.split(": ")[1];

                    System.out.println("IP address : " + ip_address);
                    System.out.println(lines[3]);
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

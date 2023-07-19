import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SmartFactoryClient {
    public static void main(String[] args) {
        String urlStr = "http://192.168.15.245";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();    // statusCode
            if(responseCode == HttpURLConnection.HTTP_OK){  //  if(responseCode == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));    // 데코레이터 패턴((()))
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    response.append(line);
                    response.append("\n");
                }
                br.close(); // buffer closed

                String[] lines = response.toString().split("\n");
                if(lines.length >= 4){
                    String ipLine = lines[2];
                    String temperatureLine = lines[3];
                    String ipAddress = ipLine.split(": ")[1];
                    String temperature = temperatureLine.split(": ")[1];

                    System.out.println("IP addr : " + ipAddress);
                    System.out.println("Temperature : " + temperature);
                }else{
                    System.out.println("Response format error!");
                }
            }else{
                System.out.println("Http connection failed : " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

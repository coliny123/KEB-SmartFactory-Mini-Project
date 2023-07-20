import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
class SQLtest {
    public static void main(String[] args) {
        Connection con = null;

        String server = "localhost"; // 서버 주소
        String user_name = "root"; //  접속자 id
        String password = "dh990921"; // 접속자 pw

        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 로드하는데에 문제 발생" + e.getMessage());
            e.printStackTrace();
        }

        // 접속
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + "?useSSL=false", user_name, password);
            System.out.println("연결 완료!");
        } catch(SQLException e) {
            System.err.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        }

        // 접속 종료
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {}
    }
}
public class SmartFactoryClient {
    public static void main(String[] args) {
        String urlStr = "http://192.168.15.138";

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

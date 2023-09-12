package Observer;

import java.util.LinkedList;
import java.util.Queue;

public class GraphDisplay implements Observer, DisplayElement {
    private static final int MAX_SIZE = 10;  // 최근 10개의 데이터만 그래프에 표시
    private Queue<Float> temperatureData;
    private SensorData sensorData;

    public GraphDisplay(SensorData sensorData) {
        this.sensorData = sensorData;
        this.sensorData.registerObserver(this);
        temperatureData = new LinkedList<>();
    }

    @Override
    public void update(float temperature, float brightness, int counting) {
        if (temperatureData.size() >= MAX_SIZE) {
            temperatureData.remove();
        }

        temperatureData.add(temperature);

        display();
    }

    @Override
    public void display() {
        System.out.println("Temperature graph: " + temperatureGraph());
    }

    private String temperatureGraph() {
        return drawGraph(temperatureData);
    }

    private String drawGraph(Queue<Float> datas) {
        StringBuilder sb = new StringBuilder();

        for (Float data : datas) {
            sb.append(data).append(", ");
        }

        return sb.toString();
    }
}

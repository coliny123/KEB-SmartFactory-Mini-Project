package Observer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class GraphDisplay extends JFrame implements Observer, DisplayElement {
    private static final int MAX_SIZE = 10;  // 최근 10개의 데이터만 그래프에 표시
    private Queue<Float> temperatureData;
    private SensorData sensorData;
    private JPanel panel;

    public GraphDisplay(SensorData sensorData) {
        this.sensorData = sensorData;
        this.sensorData.registerObserver(this);
        temperatureData = new LinkedList<>();

        // Create a simple GUI.
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        getContentPane().add(panel);
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
        repaint();
    }

    private String drawGraph(Queue<Float> datas) {
        StringBuilder sb = new StringBuilder();

        for (Float data : datas) {
            sb.append(data).append(", ");
        }

        return sb.toString();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        int x1 = 50; // start point of the graph.

        for (Float temp : temperatureData) {
            int y1 = getHeight() - Math.round(temp * 10); // scale it up by a factor of 10 for visibility.
            g2d.drawLine(x1, y1, x1 + 20, y1);
            x1 += 20;
        }
    }
}

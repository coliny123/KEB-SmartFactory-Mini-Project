package Observer;

import javax.swing.*;
import java.awt.*;

public class StatisticsDisplay extends JFrame implements Observer, DisplayElement {
	private float maxTemp = 0.0f;
	private float minTemp = 200f;
	private float tempSum= 0.0f;
	private int numReadings;
	private SensorData weatherData;

	private JLabel avgTemperature;
	private JLabel maxTemperature;
	private JLabel minTemperature;

	public StatisticsDisplay(SensorData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);

		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3,1));

		avgTemperature=new JLabel();
		maxTemperature=new JLabel();
		minTemperature=new JLabel();

		add(avgTemperature);
		add(maxTemperature);
		add(minTemperature);

		setVisible(true); // Make the window visible

	}

	@Override public void display() {
		avgTemperature.setText("Avg Temp: "+ (tempSum / numReadings));
		maxTemperature.setText("Max Temp: "+maxTemp );
		minTemperature.setText("Min Temp: "+minTemp );
	}

	@Override public void update(float temperature, float brightness, int counting) {
		tempSum += temperature;
		numReadings++;

		if (temperature > maxTemp) {
			maxTemp = temperature;
		}

		if (temperature < minTemp) {
			minTemp = temperature;
		}

		display();
	}
}

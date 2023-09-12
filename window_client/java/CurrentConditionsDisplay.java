package Observer;

import javax.swing.*;
import java.awt.*;

public class CurrentConditionsDisplay extends JFrame implements Observer, DisplayElement {
	private float temperature;
	private float brightness;
	private int counting;
	private SensorData sensorData;

	// GUI components
	private JLabel temperatureLabel;
	private JLabel brightnessLabel;
	private JLabel countingLabel;

	public CurrentConditionsDisplay(SensorData sensorData) {
		this.sensorData = sensorData;
		sensorData.registerObserver(this);

		// Create a simple GUI.
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));

		temperatureLabel = new JLabel();
		brightnessLabel = new JLabel();
		countingLabel = new JLabel();

		add(temperatureLabel);
		add(brightnessLabel);
		add(countingLabel);

		setVisible(true); // Make the window visible
	}

	@Override
	public void update(float temperature, float brightness, int counting) {
		this.temperature = temperature;
		this.brightness = brightness;
		this.counting = counting;

		display();
	}

	@Override
	public void display() {
		temperatureLabel.setText("Temperature: " + this.temperature + "C");
		brightnessLabel.setText("Brightness: " + this.brightness);
		countingLabel.setText("Counting: " + this.counting + "개");
	}
}

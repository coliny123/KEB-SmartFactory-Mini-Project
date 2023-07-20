package Observer;
	
public class CurrentConditionsDisplay implements Observer, DisplayElement {
	private float temperature;
	private float brightness;

	private int counting;
	private SensorData sensorData;
	
	public CurrentConditionsDisplay(SensorData sensorData) {
		this.sensorData = sensorData;
		sensorData.registerObserver(this);
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
		System.out.println("Current conditions: " + temperature 
			+ "C / " + brightness + " / " + counting +" 개");
	}

}

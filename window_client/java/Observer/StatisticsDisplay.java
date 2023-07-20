//package Observer;
//
//public class StatisticsDisplay implements Observer, DisplayElement {
//	private float maxTemp = 0.0f;
//	private float minTemp = 200;
//	private float tempSum= 0.0f;
//	private int numReadings;
//	private SensorData sensorData;
//
//	public StatisticsDisplay(SensorData sensorData) {
//		this.sensorData = sensorData;
//		sensorData.registerObserver(this);
//	}
//
//
//	@Override
//	public void display() {
//		System.out.println("Avg/Max/Min temperature = " + (tempSum / numReadings)
//			+ "/" + maxTemp + "/" + minTemp);
//	}
//
//	@Override
//	public void update(float temperature, float brightness, int counting) {
//		tempSum += temperature;
//		numReadings++;
//
//		if (temperature > maxTemp) {
//			maxTemp = temperature;
//		}
//
//		if (temperature < minTemp) {
//			minTemp = temperature;
//		}
//
//		display();
//	}
//}

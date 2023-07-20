package Observer;

import java.util.*;

public class SensorData implements Subject {
    private List<Observer> observers;
    private float temperature;
    private float brightness;
    private int counting;

    public SensorData(){
        observers = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, brightness, counting);
        }
    }
    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temperature, float brightness, int counting) {
        this.temperature = temperature;
        this.brightness = brightness;
        this.counting = counting;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getBrightness() {
        return brightness;
    }

    public int getCounting() {
        return counting;
    }
}

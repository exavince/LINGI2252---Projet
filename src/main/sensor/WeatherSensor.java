package main.sensor;

public class WeatherSensor extends Sensor {
    @Override
    public void trigger(Object message) {
        getHouse().send("weather_report");
    }
}

package main.sensor;

public class WeatherSensor implements Sensor {
    @Override
    public String askReport() {
        return "weather_report";
    }
}

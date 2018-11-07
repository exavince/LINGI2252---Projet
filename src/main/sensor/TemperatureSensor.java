package main.sensor;

public class TemperatureSensor implements Sensor {
    @Override
    public String askReport() {
        return "temperature_report";
    }
}

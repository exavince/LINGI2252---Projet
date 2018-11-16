package main.sensor;

import main.WeatherStatus;
import main.message.WeatherReport;

public class WeatherSensor extends Sensor {
    @Override
    public void trigger(Object message) {
        WeatherStatus status = getHouse().getWeatherStatus();
        getHouse().sendToItems(new WeatherReport(status));
    }
}

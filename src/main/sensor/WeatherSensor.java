package main.sensor;

import main.WeatherStatus;
import main.message.WeatherReport;

public class WeatherSensor extends Sensor {
    @Override
    public void trigger(Object message) {
        if (message != "check_weather") return;
        WeatherStatus status = getHouse().getWeatherStatus();
        getHouse().sendToItems(new WeatherReport(status));
    }
}

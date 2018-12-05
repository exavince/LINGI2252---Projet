package main.sensor;

import main.WeatherStatus;
import main.item.Item;
import main.message.WeatherReport;

public class WeatherSensor extends Item {
    @Override
    public void onEvent(Object message) {
        if (message != "check_weather") return;
        WeatherStatus status = getHouse().getWeatherStatus();
        getHouse().sendToItems(new WeatherReport(status));
    }
}

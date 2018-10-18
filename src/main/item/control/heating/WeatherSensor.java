package main.item.control.heating;

import main.ConnectedHouse;
import main.item.Sensor;
import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;
import main.event.WeatherReport;

public class WeatherSensor implements Sensor {
    @Override
    public void onTrigger(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof SoonWakeUpTime) {
            System.out.println("\tWeather sensors: Checking temperature");
            house.broadcast(new WeatherReport());
        }
    }
}

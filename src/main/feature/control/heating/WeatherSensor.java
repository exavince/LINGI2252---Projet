package main.feature.control.heating;

import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;
import main.event.WeatherReport;
import main.feature.Feature;

import static main.ConnectedHouseSimulator.VIRTUAL_BUS;

public class WeatherSensor implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof SoonWakeUpTime) {
            System.out.println("\tWeather sensors: Checking temperature");
            VIRTUAL_BUS.post(new WeatherReport());
        }

    }
}

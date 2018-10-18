package main.connected.control.heating;

import main.ConnectedHouse;
import main.connected.Feature;
import main.event.SimulationEvent;
import main.event.WeatherReport;

public class HeatingController implements Feature {

    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof WeatherReport) {
            System.out.println("Adjusting heating accordingly..");
        }
    }
}

package main.feature.control.heating;

import main.event.SimulationEvent;
import main.event.WeatherReport;
import main.feature.Feature;

public class HeatingController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof WeatherReport) {
            System.out.println("Adjusting heating accordingly..");
        }
    }
}

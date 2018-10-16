package main.feature;

import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;

public class HeatingController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof SoonWakeUpTime) {
            System.out.println("\tWeather sensors: Checking temperature");
            System.out.println("Adjusting heating accordingly..");
        }
    }
}

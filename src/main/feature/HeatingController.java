package feature;

import event.SimulationEvent;
import event.SoonWakeUpTime;

public class HeatingController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof SoonWakeUpTime) {
            System.out.println("\tWeather sensors: Checking temperature");
            System.out.println("Adjusting heating accordingly..");
        }
    }
}

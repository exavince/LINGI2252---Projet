package main.feature;

import main.ConnectedHouseSimulator;
import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.WakeUpTime;

public class ClockController implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof WakeUpTime) {
            ConnectedHouseSimulator.EVENT_BUS.post(new SoundEvent("WAKE UP USER, WAKE UP !"));
        }

    }
}

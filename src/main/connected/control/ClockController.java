package main.connected.control;

import main.ConnectedHouse;
import main.connected.Feature;
import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.WakeUpTime;

public class ClockController implements Feature {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof WakeUpTime) {
            house.broadcast(new SoundEvent("WAKE UP USER, WAKE UP !"));
        }
    }
}

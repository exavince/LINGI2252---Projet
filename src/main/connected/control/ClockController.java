package main.connected.control;

import main.ConnectedHouse;
import main.connected.Item;
import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.WakeUpTime;

public class ClockController implements Item {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof WakeUpTime) {
            house.broadcast(new SoundEvent("WAKE UP USER, WAKE UP !"));
        }
    }
}

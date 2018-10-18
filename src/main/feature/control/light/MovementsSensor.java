package main.feature.control.light;

import main.event.RoomEvent;
import main.event.SimulationEvent;
import main.feature.Feature;

import static main.ConnectedHouseSimulator.VIRTUAL_BUS;

public class MovementsSensor implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof RoomEvent) {
            VIRTUAL_BUS.post(event);
        }
    }
}

package main.connected;

import main.ConnectedHouse;
import main.event.SimulationEvent;

public interface Feature {
    void onEvent(SimulationEvent event, ConnectedHouse house);
}

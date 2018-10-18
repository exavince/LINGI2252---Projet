package main.connected;

import main.ConnectedHouse;
import main.event.SimulationEvent;

public interface Item {
    void onEvent(SimulationEvent event, ConnectedHouse house);
}

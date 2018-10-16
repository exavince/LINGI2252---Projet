package main.feature;

import main.event.SimulationEvent;

public interface Feature {
    void onEvent(SimulationEvent event);
}

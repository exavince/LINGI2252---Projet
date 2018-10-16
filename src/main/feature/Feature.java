package feature;

import event.SimulationEvent;

public interface Feature {
    void onEvent(SimulationEvent event);
}

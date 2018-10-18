package main;

import main.event.SimulationEvent;

public interface Sensor {
    /**
     * Action that should happen when the sensor is triggered by some real-life event
     *
     * @param event The real-life event that happened
     * @param house Event bus where to send virtual messages to the house
     */
    void onTrigger(SimulationEvent event, ConnectedHouse house);
}

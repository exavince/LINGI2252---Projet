package main;

import main.connected.Feature;
import main.event.SimulationEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectedHouse {
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Feature> features = new ArrayList<>();

    /**
     * Adds features to the Event bus. At least one feature is required to call the method.
     */
    void register(Feature minimumFeature, Feature... featuresIn) {
        features.add(minimumFeature);
        features.addAll(Arrays.asList(featuresIn));
    }

    /**
     * Broadcast an event to all features.
     *
     * @param event The main.event to be broadcasted
     */
    public void broadcast(SimulationEvent event) {
        features.forEach(feature -> feature.onEvent(event, this));
    }

    /**
     * Adds sensors. At least one sensor is required to call the method.
     */
    void register(Sensor minimumSensor, Sensor... sensorsIn) {
        sensors.add(minimumSensor);
        sensors.addAll(Arrays.asList(sensorsIn));
    }

    void triggerSensors(SimulationEvent event) {
        sensors.forEach(sensor -> sensor.onTrigger(event, this));
    }
}

package main;

import main.event.SimulationEvent;
import main.feature.Feature;

import java.util.ArrayList;
import java.util.Arrays;

public class EventBus {
    private ArrayList<Feature> features = new ArrayList<>();

    /**
     * Adds features to the Event bus. At least one main.feature is required to call the method.
     */
    public void register(Feature minimumFeature, Feature... featuresIn) {
        features.add(minimumFeature);
        features.addAll(Arrays.asList(featuresIn));
    }

    /**
     * Trigger an main.event. All features will be notified.
     *
     * @param event The main.event to be triggered
     */
    public void post(SimulationEvent event) {
        features.forEach(feature -> feature.onEvent(event));
    }
}

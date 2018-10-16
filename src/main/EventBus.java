import event.SimulationEvent;
import feature.Feature;

import java.util.ArrayList;
import java.util.Arrays;

public class EventBus {
    private ArrayList<Feature> features = new ArrayList<>();

    /**
     * Adds features to the Event bus. At least one feature is required to call the method.
     */
    public void register(Feature minimumFeature, Feature ... featuresIn) {
        features.add(minimumFeature);
        features.addAll(Arrays.asList(featuresIn));
    }

    /**
     * Trigger an event. All features will be notified.
     * @param event The event to be triggered
     */
    public void post(SimulationEvent event) {
        features.forEach(feature -> feature.onEvent(event));
    }
}

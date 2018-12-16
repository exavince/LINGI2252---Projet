package framework;

/**
 * Needed because while we have a unique feature model at the moment, there are multiple configurations.
 * Decoupled from the FeatureModel itself because we might imagine two feature models with the same feature set.
 */
public interface FeatureModelConfiguration {
    // Am I valid in this state
    boolean isActivated(Feature feature);

    /**
     * @param feature The feature
     * @return True if the feature was added, false if it was already there
     */
    <T> boolean activate(Feature<T> feature);

    /**
     * @param feature The feature
     * @return True if the feature was disabled, false if it was already disabled
     */
    <T> boolean deactivate(Feature<T> feature);

    /**
     * Meant to be used to try new changes and verify them.
     *
     * @return A new feature model configuration with the same properties.
     */
    FeatureModelConfiguration copy();
}

package framework.editing;

import framework.FeatureModelConfiguration;
import framework.InvalidModelConfigurationException;

import java.util.List;

@FunctionalInterface
public interface FeatureEditingStrategy {
    /**
     * Try to apply a list of changes
     *
     * @param modelConfiguration The model configuration
     * @param actions            The changes
     * @throws InvalidModelConfigurationException When it was not possible because the changes resulted in an invalid model
     */
    void apply(FeatureModelConfiguration modelConfiguration, List<FeatureAction> actions) throws InvalidModelConfigurationException;
}

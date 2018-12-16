package framework.editing;

import framework.FeatureModel;
import framework.FeatureModelConfiguration;
import framework.InvalidModelConfigurationException;

import java.util.List;

public class TryOnCopyStrategy implements FeatureEditingStrategy {
    private final FeatureModel model;

    public TryOnCopyStrategy(FeatureModel model) {
        this.model = model;
    }

    @Override
    public <T> void apply(FeatureModelConfiguration modelConfiguration, List<FeatureAction<T>> actions) throws InvalidModelConfigurationException {
        FeatureModelConfiguration copy = modelConfiguration.copy();
        actions.forEach(action -> action.execute(copy));
        boolean valid = model.interpret(copy);
        if (valid) {
            commitActions(modelConfiguration, actions);
        } else throw new InvalidModelConfigurationException(copy, model);
    }

    private <T> void commitActions(FeatureModelConfiguration modelConfiguration, List<FeatureAction<T>> actions) {
        actions.forEach(action -> {
            action.execute(modelConfiguration);
            action.apply();
        });
    }
}

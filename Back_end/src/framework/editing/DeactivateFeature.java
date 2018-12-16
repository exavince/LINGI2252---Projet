package framework.editing;

import framework.Feature;
import framework.FeatureModelConfiguration;

public class DeactivateFeature<T> extends FeatureAction<T> {

    public DeactivateFeature(Feature<T> feature, T target) {
        super(feature, target);
    }

    @Override
    public void execute(FeatureModelConfiguration model) {
        model.deactivate(getFeature());
    }

    @Override
    public void apply() {
        getFeature().deactivate(getTarget());
    }
}

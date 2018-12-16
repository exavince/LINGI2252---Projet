package framework.editing;

import framework.Feature;
import framework.FeatureModelConfiguration;

public class ActivateFeature<T> extends FeatureAction<T> {

    public ActivateFeature(Feature<T> feature, T target) {
        super(feature, target);
    }

    @Override
    public void execute(FeatureModelConfiguration model) {
        model.activate(getFeature());
    }

    @Override
    public void apply() {
        getFeature().activate(getTarget());
    }
}

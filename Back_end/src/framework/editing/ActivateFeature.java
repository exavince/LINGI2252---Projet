package framework.editing;

import framework.ConcreteFeature;
import framework.Feature;
import framework.FeatureModelConfiguration;

public class ActivateFeature<T> extends FeatureAction<T> {

    public ActivateFeature(Feature feature, T target) {
        super(feature, target);
    }

    @Override
    public void execute(FeatureModelConfiguration model) {
        model.activate(getFeature());
    }

    @Override
    public void apply() {
        if (getFeature() instanceof ConcreteFeature) {
            ((ConcreteFeature<T>) getFeature()).activate(getTarget());
        }
    }
}

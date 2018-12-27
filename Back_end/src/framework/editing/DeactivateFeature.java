package framework.editing;

import framework.ConcreteFeature;
import framework.Feature;
import framework.FeatureModelConfiguration;

public class DeactivateFeature<T> extends FeatureAction<T> {

    public DeactivateFeature(Feature feature, T target) {
        super(feature, target);
    }

    @Override
    public void execute(FeatureModelConfiguration model) {
        model.deactivate(getFeature());
    }

    @Override
    public void apply() {
        if (getFeature() instanceof ConcreteFeature) {
            ((ConcreteFeature<T>) getFeature()).deactivate(getTarget());
        }
    }
}

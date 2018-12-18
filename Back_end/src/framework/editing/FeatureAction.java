package framework.editing;

import framework.Feature;
import framework.FeatureModelConfiguration;

public abstract class FeatureAction<T> {
    private final Feature<T> feature;
    private final T target;

    public FeatureAction(Feature<T> feature, T target) {
        this.feature = feature;
        this.target = target;
    }

    protected Feature<T> getFeature() {
        return feature;
    }

    protected T getTarget() {
        return target;
    }

    abstract void execute(FeatureModelConfiguration model);

    public abstract void apply();
}

package framework;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class FeatureModelConfigurationImpl implements FeatureModelConfiguration {
    private final Collection<Feature> activatedFeatures = new HashSet<>();

    @Override
    public boolean isActivated(Feature feature) {
        return activatedFeatures.contains(feature);
    }

    @Override
    public boolean activate(Feature feature) {
        if (isActivated(feature)) return false;
        activatedFeatures.add(feature);
        return true;
    }

    @Override
    public boolean deactivate(Feature feature) {
        if (!isActivated(feature)) return false;
        activatedFeatures.remove(feature);
        return true;
    }

    @Override
    public FeatureModelConfiguration copy() {
        FeatureModelConfigurationImpl copy = new FeatureModelConfigurationImpl();
        copy.activatedFeatures.addAll(this.activatedFeatures);
        return copy;
    }

    @Override
    public String toString() {
        String features = activatedFeatures.stream().map(Object::toString).collect(Collectors.joining(" "));
        return "[" + activatedFeatures.size() + " " + features + "]";
    }
}

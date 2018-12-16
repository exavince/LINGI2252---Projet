package framework;

import framework.constraint.Constraint;
import framework.primitive.Primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FeatureModel<T> {
    private static final Logger LOGGER = Logger.getLogger(FeatureModel.class.getName());
    private final List<Feature<T>> features = new ArrayList<>();
    private final List<Primitive> featureDiagram = new ArrayList<>();
    private final List<Constraint> crossTreeConstraints = new ArrayList<>();
    // TODO Based on a string, one should be able to get the feature for command line

    @SafeVarargs
    protected final void addFeatures(Feature<T>... featuresIn) {
        features.addAll(Arrays.asList(featuresIn));
    }

    protected final void addFeatureDiagramPrimitives(Primitive... primitivesIn) {
        featureDiagram.addAll(Arrays.asList(primitivesIn));
    }

    protected final void addCrossTreeConstraints(Constraint... constraintsIn) {
        crossTreeConstraints.addAll(Arrays.asList(constraintsIn));
    }

    public final Feature<T> getFeature(String name) {
        // TODO Error if feature name does not make sense in our model ?
        return features.stream().filter(feature -> feature.getName().toUpperCase().equals(name.toUpperCase())).findFirst().orElse(null);
    }

    /**
     * @param context the configuration
     * @return Whether the configuration is valid or not
     */
    public boolean interpret(FeatureModelConfiguration context) {
        boolean valid = true;
        for (Constraint rule : featureDiagram) {
            String output = "[Feature diagram]\t" + rule + ": ";
            if (!rule.interpret(context)) {
                LOGGER.log(Level.SEVERE, output + "NOT VALID");
                valid = false;
            }
        }
        for (Constraint rule : crossTreeConstraints) {
            String output = "[Cross-tree constraint]\t" + rule + ": ";
            if (!rule.interpret(context)) {
                LOGGER.log(Level.SEVERE, output + "NOT VALID");
                valid = false;
            }
        }
        return valid;
    }
}

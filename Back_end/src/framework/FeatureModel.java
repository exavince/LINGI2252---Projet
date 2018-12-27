package framework;

import framework.constraint.Constraint;
import framework.primitive.Primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FeatureModel {
    private static final Logger LOGGER = Logger.getLogger(FeatureModel.class.getName());
    private final List<Feature> features = new ArrayList<>();
    private final List<Primitive> featureDiagram = new ArrayList<>();
    private final List<Constraint> crossTreeConstraints = new ArrayList<>();

    protected final void addFeatures(Feature... featuresIn) {
        features.addAll(Arrays.asList(featuresIn));
    }

    protected final void addFeatureDiagramPrimitives(Primitive... primitivesIn) {
        featureDiagram.addAll(Arrays.asList(primitivesIn));
    }

    protected final void addCrossTreeConstraints(Constraint... constraintsIn) {
        crossTreeConstraints.addAll(Arrays.asList(constraintsIn));
    }

    public final Feature getFeature(String name) {
        Feature feature = features.stream().filter(f -> f.getName().toUpperCase().equals(name.toUpperCase())).findFirst().orElse(null);
        if (feature == null) {
            LOGGER.log(Level.SEVERE, "Feature " + name + " not present in feature model.");
        }
        return feature;
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

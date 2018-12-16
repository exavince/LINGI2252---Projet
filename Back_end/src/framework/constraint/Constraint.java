package framework.constraint;

import framework.FeatureModelConfiguration;

public interface Constraint {
    boolean interpret(FeatureModelConfiguration context);
}

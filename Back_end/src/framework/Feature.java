package framework;

import framework.constraint.Constraint;

public interface Feature extends Constraint {
    String getName();

    /**
     * @return Whether the feature is activated or not
     */
    @Override
    default boolean interpret(FeatureModelConfiguration context) {
        return context.isActivated(this);
    }
}

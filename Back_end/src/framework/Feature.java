package framework;

import framework.constraint.Constraint;

public interface Feature<T> extends Constraint {
    String getName();

    void activate(T target);

    void deactivate(T target);

    /**
     * @return Whether the feature is activated or not
     */
    @Override
    default boolean interpret(FeatureModelConfiguration context) {
        return context.isActivated(this);
    }
}

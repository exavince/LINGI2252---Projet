package framework.constraint;

import framework.FeatureModelConfiguration;

public class DoubleImplication implements Constraint {
    private final Constraint left;
    private final Constraint right;

    public DoubleImplication(Constraint left, Constraint right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(FeatureModelConfiguration context) {
        return new LogicalAnd(new Implication(left, right), new Implication(right, left)).interpret(context);
    }

    @Override
    public String toString() {
        return left.toString() + " <=> " + right.toString();
    }
}

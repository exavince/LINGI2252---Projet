package framework.constraint;

import framework.FeatureModelConfiguration;

public class Not implements Constraint {
    private final Constraint content;

    public Not(Constraint content) {
        this.content = content;
    }

    @Override
    public boolean interpret(FeatureModelConfiguration context) {
        return !content.interpret(context);
    }

    @Override
    public String toString() {
        return "Not(" + content.toString() + ")";
    }
}

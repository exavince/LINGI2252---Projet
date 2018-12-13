package main.primitive;

import main.constraint.Constraint;
import main.constraint.Implication;
import main.constraint.LogicalAnd;
import main.parametrization.Feature;

import java.util.stream.Collectors;

public class Optional extends Primitive {
    public Optional(Feature parent, Feature... subfeatures) {
        super(parent, subfeatures);
    }

    @Override
    public Constraint getPropositionalFormula() {
        return new LogicalAnd(getSubfeatures().stream().map(sub -> new Implication(sub, getParent())).collect(Collectors.toList()));
    }
}

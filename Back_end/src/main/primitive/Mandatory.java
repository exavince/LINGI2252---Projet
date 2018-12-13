package main.primitive;

import main.constraint.Constraint;
import main.constraint.DoubleImplication;
import main.constraint.LogicalAnd;
import main.parametrization.Feature;

import java.util.stream.Collectors;

public class Mandatory extends Primitive {
    protected Mandatory(Feature parent, Feature... subfeatures) {
        super(parent, subfeatures);
    }

    @Override
    public Constraint getPropositionalFormula() {
        return new LogicalAnd(getSubfeatures().stream().map(sub -> new DoubleImplication(sub, getParent())).collect(Collectors.toList()));
    }
}

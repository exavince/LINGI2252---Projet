package main.primitive;

import main.constraint.Constraint;
import main.constraint.DoubleImplication;
import main.constraint.LogicalOr;
import main.parametrization.Feature;

import java.util.ArrayList;

public class Or extends Primitive {
    public Or(Feature parent, Feature... subfeatures) {
        super(parent, subfeatures);
    }

    @Override
    public Constraint getPropositionalFormula() {
        return new DoubleImplication(new LogicalOr(new ArrayList<>(getSubfeatures())), getParent());
    }
}

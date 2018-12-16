package framework.primitive;

import framework.Feature;
import framework.constraint.Constraint;
import framework.constraint.DoubleImplication;
import framework.constraint.LogicalOr;

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

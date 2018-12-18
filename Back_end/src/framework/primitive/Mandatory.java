package framework.primitive;

import framework.Feature;
import framework.constraint.Constraint;
import framework.constraint.DoubleImplication;
import framework.constraint.LogicalAnd;

import java.util.stream.Collectors;

public class Mandatory extends Primitive {
    public Mandatory(Feature parent, Feature... subfeatures) {
        super(parent, subfeatures);
    }

    @Override
    public Constraint getPropositionalFormula() {
        return new LogicalAnd(getSubfeatures().stream().map(sub -> new DoubleImplication(sub, getParent())).collect(Collectors.toList()));
    }
}

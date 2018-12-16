package framework.primitive;

import framework.Feature;
import framework.constraint.Constraint;
import framework.constraint.Implication;
import framework.constraint.LogicalAnd;

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

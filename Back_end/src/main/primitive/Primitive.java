package main.primitive;

import main.Room;
import main.constraint.Constraint;
import main.parametrization.Feature;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Primitive implements Constraint {
    private final Feature parent;
    private final List<Feature> subfeatures;

    Primitive(Feature parent, Feature... subfeatures) {
        this.parent = parent;
        this.subfeatures = Arrays.asList(subfeatures);
    }

    Feature getParent() {
        return parent;
    }

    List<Feature> getSubfeatures() {
        return subfeatures;
    }

    abstract Constraint getPropositionalFormula();

    @Override
    public String toString() {
        return "[" + getSubfeatures().stream().map(Object::toString).collect(Collectors.joining(", ")) + " " + this.getClass().getSimpleName() + " sub-features of " + getParent() + "]";
    }

    // TODO cache the propositional formula
    @Override
    public final boolean interpret(Room context) {
        return getPropositionalFormula().interpret(context);
    }
}

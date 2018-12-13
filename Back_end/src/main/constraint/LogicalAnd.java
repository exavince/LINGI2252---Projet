package main.constraint;

import main.Room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogicalAnd implements Constraint {
    private final List<Constraint> constraints;

    public LogicalAnd(Constraint... constraintsIn) {
        this(Arrays.asList(constraintsIn));
    }

    public LogicalAnd(List<Constraint> constraintsIn) {
        this.constraints = constraintsIn;
    }

    @Override
    public boolean interpret(Room context) {
        for (Constraint expr : constraints) {
            if (!expr.interpret(context)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return constraints.stream().map(Object::toString).collect(Collectors.joining(" AND "));
    }
}

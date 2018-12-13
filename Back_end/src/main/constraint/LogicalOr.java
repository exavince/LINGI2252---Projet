package main.constraint;

import main.Room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogicalOr implements Constraint {
    private final List<Constraint> constraints;

    public LogicalOr(Constraint... constraintsIn) {
        this(Arrays.asList(constraintsIn));
    }

    public LogicalOr(List<Constraint> constraintsIn) {
        this.constraints = constraintsIn;
    }

    @Override
    public boolean interpret(Room context) {
        for (Constraint expr : constraints) {
            if (expr.interpret(context)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return constraints.stream().map(Object::toString).collect(Collectors.joining(" OR "));
    }
}

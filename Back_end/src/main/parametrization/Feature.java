package main.parametrization;

import main.Room;
import main.constraint.Constraint;

public interface Feature extends Constraint {
    void enable(Room context);

    void disable(Room context);
}

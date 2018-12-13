package main.constraint;

import main.Room;

public interface Constraint {
    boolean interpret(Room context);
}

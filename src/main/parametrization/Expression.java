package main.parametrization;

import main.Room;

public interface Expression {
    boolean interpret(Room context);
}

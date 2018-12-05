package main.parametrization;

import main.Room;

public interface Feature extends Expression {
    void enable(Room context);
    void disable(Room context);
}

package main.parametrization;

import main.Room;

public class AbstractFeature implements Feature {
    private boolean value = false;
    private final String name;

    AbstractFeature(String name) {
        this.name = name;
    }

    @Override
    public void enable(Room context) {
        value = true;
    }

    @Override
    public void disable(Room context) {
        value = false;

    }

    @Override
    public boolean interpret(Room context) {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
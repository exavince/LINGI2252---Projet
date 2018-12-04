package main.command;

import main.ConnectedHouse;

@FunctionalInterface
public interface Command {
    Command EXIT = house -> {
        throw new UnsupportedOperationException("EXIT command should not be interpreted.");
    };
    Command DONE = house -> {
        throw new UnsupportedOperationException("DONE command should not be interpreted.");
    };

    void interpret(ConnectedHouse house);
}

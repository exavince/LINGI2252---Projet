package main.command;

import main.ConnectedHouse;

@FunctionalInterface
public interface Command {
    void execute(ConnectedHouse house);
}

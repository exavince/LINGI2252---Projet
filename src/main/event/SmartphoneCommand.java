package main.event;

public enum SmartphoneCommand implements SimulationEvent {
    // TODO This could be handled in a better way to add many commands, but that's not the point of the simulation
    OPEN_GARAGE_DOOR,
    LOCK_HOUSE
}

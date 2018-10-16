package main.event;

import main.Room;

public class EnterTheRoom implements SimulationEvent {
    private Room room;

    public EnterTheRoom(Room room) {
        this.room = room;
    }
}


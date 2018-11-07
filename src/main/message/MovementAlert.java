package main.message;

import main.Room;

public class MovementAlert {
    private final Room room;

    public MovementAlert(Room roomIn) {
        this.room = roomIn;
    }

    public Room getRoom() {
        return room;
    }
}

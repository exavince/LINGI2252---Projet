package main.event;

import main.Room;

/**
 * RoomEvents both represent the "physical" movement in the room detected by movement sensors and the message broadcasted by these sensors.
 */
public abstract class RoomEvent implements SimulationEvent {
    private Room room;

    RoomEvent(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}


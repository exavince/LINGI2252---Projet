package main.event;

import main.RoomType;

/**
 * RoomEvents both represent the "physical" movement in the roomType detected by movement sensors and the message broadcasted by these sensors.
 */
public abstract class RoomEvent implements SimulationEvent {
    private RoomType roomType;

    RoomEvent(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}


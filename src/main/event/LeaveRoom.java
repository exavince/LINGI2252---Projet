package main.event;

import main.Room;

public class LeaveRoom extends RoomEvent {
    public LeaveRoom(Room room) {
        super(room);
    }
}


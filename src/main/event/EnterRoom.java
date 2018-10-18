package main.event;

import main.Room;

public class EnterRoom extends RoomEvent {
    public EnterRoom(Room room) {
        super(room);
    }
}


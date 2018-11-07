package main.event;

import main.RoomType;

public class LeaveRoom extends RoomEvent {
    public LeaveRoom(RoomType roomType) {
        super(roomType);
    }
}


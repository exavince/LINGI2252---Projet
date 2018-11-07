package main.event;

import main.RoomType;

public class EnterRoom extends RoomEvent {
    public EnterRoom(RoomType roomType) {
        super(roomType);
    }
}


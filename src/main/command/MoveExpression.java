package main.command;

import main.ConnectedHouse;
import main.RoomType;

public class MoveExpression implements Command {
    private final RoomType roomType;

    MoveExpression(RoomType roomType) {
        if(roomType == RoomType.GLOBAL) throw new IllegalArgumentException("Cannot move to room type "+roomType);
        this.roomType = roomType;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        house.moveTo(roomType);
    }

}

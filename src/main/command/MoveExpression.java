package main.command;

import main.ConnectedHouse;
import main.RoomType;

public class MoveExpression implements Command {
    private final RoomType roomType;

    MoveExpression(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        house.moveTo(roomType);
    }

}

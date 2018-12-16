package main.command;

import main.ConnectedHouse;
import main.RoomType;

import java.util.logging.Level;

public class MoveExpression implements Command {
    private final RoomType roomType;

    MoveExpression(RoomType roomType) {
        if (roomType == RoomType.GLOBAL) throw new IllegalArgumentException("Cannot move to room type " + roomType);
        this.roomType = roomType;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        if (house.getPosition() == roomType) {
            CommandParser.LOGGER.log(Level.WARNING, "User was already in room " + roomType);
        }
        house.moveTo(roomType);
        CommandParser.LOGGER.log(Level.INFO, "## Moved to room " + roomType + " with success.");
    }

}
